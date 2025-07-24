package io.github.byzatic.pqletta.client.impl.query_range;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.byzatic.commons.base_exceptions.OperationIncompleteException;
import io.github.byzatic.pqletta.client.dto.request.impl.PrometheusQueryRangeRequest;
import io.github.byzatic.pqletta.client.dto.response.PrometheusResponse;
import io.github.byzatic.pqletta.client.dto.response.impl.error.GenericClientTransportError;
import io.github.byzatic.pqletta.client.dto.response.impl.error.GenericPrometheusApiError;
import io.github.byzatic.pqletta.client.exceptions.InternalClientException;
import io.github.byzatic.pqletta.client.impl.RangeQueryExecutorInterface;
import io.github.byzatic.pqletta.client.impl.query_range.response_handler.PrometheusResponseHandler;
import io.github.byzatic.pqletta.client.impl.query_range.support.InternalHttpLoggingInterceptor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/*
Часто встречающиеся подтипы IOException в OkHttp:

java.net.ConnectException
Не удалось подключиться к серверу (например, нет сети или отказ в соединении)

java.net.UnknownHostException
DNS-имя хоста не может быть разрешено (например, опечатка в URL или нет интернета)

java.net.SocketTimeoutException
Время ожидания соединения или чтения ответа истекло

javax.net.ssl.SSLHandshakeException
Ошибка во время SSL-рукопожатия (неверный сертификат, неподдерживаемый TLS)

javax.net.ssl.SSLPeerUnverifiedException
Сертификат сервера не может быть проверен

java.io.EOFException
Оборванное соединение, неожиданный конец потока

java.net.ProtocolException
Ошибка в HTTP-протоколе (например, неожиданный статус или заголовок)

java.io.InterruptedIOException
Поток был прерван во время ожидания

java.io.FileNotFoundException
При доступе к локальным файлам (редко, например, если ты используешь file://)
 */

public class RangeQueryExecutor implements RangeQueryExecutorInterface {
    private final static Logger logger = LoggerFactory.getLogger(RangeQueryExecutor.class);
    private Integer connectTimeout = null;
    private Integer readTimeout = null;
    private final PrometheusResponseHandlerInterface prometheusResponseHandlerInterface = new PrometheusResponseHandler();

    public RangeQueryExecutor(Integer connectTimeout, Integer readTimeout) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    @Override
    public PrometheusResponse executePrometheusQuery(PrometheusQueryRangeRequest prometheusQueryRangeRequest) throws OperationIncompleteException {
        try {
            logger.debug("Process prometheus api request {}", prometheusQueryRangeRequest);

            PrometheusResponse prometheusServerResponse = processQueryRange(prometheusQueryRangeRequest);

            logger.debug("Return prometheus api query_range {}", prometheusServerResponse);
            return prometheusServerResponse;
        } catch (Exception e) {
            throw new OperationIncompleteException(e);
        }
    }

    @NotNull
    private PrometheusResponse processQueryRange(@NotNull PrometheusQueryRangeRequest prometheusQueryRangeRequest) throws InternalClientException {

        // Подготовка клиента
        OkHttpClient client = getOkHttpClient(prometheusQueryRangeRequest);

        // Формирование URL
        HttpUrl url = prepareHttpUrl(prometheusQueryRangeRequest);

        // Формирование Request
        Request request = new Request.Builder()
                .url(url)
                .build();

        PrometheusResponse result;

        try (Response response = client.newCall(request).execute()) {

            @Nullable
            String responseBody = response.body() != null ? response.body().string() : null;

            // response.isSuccessful() Возвращает true, если HTTP-статус ответа находится в диапазоне от 200 до 299 включительно — то есть, успешный ответ по HTTP-стандарту.
            if (response.isSuccessful()) {
                if (responseBody == null) throw new InternalClientException("Can't deserialize null response body");

                logger.debug("Client call is successful; HTTP code: " + response.code() + " - " + response.message());

                result = prometheusResponseHandlerInterface.processResponse(responseBody, response.code());

            } else {
                logger.warn("Client call is not successful; Unexpected HTTP code: " + response.code() + " - " + response.message());

                result = prometheusApiHandler(response.code(), responseBody);
            }
        } catch (InternalClientException e) {
                throw new InternalClientException(e);
        } catch (UnknownHostException e) {
            logger.error("Host could not be resolved", e);
            result = getGenericClientTransportError(e);
        } catch (ConnectException e) {
            logger.error("Connection refused", e);
            result = getGenericClientTransportError(e);
        } catch (SocketTimeoutException e) {
            logger.error("Connection timed out", e);
            result = getGenericClientTransportError(e);
        } catch (SSLHandshakeException e) {
            logger.error("SSL handshake failed", e);
            result = getGenericClientTransportError(e);
        } catch (IOException e) {
            logger.error("General I/O error during HTTP call", e);
            result = getGenericClientTransportError(e);
        } catch (Exception e) {
            logger.error("Undefined error", e);
            result = getGenericClientTransportError(e);
        }
        return result;
    }


    private GenericClientTransportError getGenericClientTransportError(Throwable throwable) {

        return GenericClientTransportError.newBuilder()
                .setMessage(throwable.getMessage())
                .setCause(throwable)
                .build();
    }

    private GenericPrometheusApiError getGenericPrometheusApiError(String errorMessage, Integer httpErrorCode, String responseBody) {

        return GenericPrometheusApiError.newBuilder()
                .setHttpStatusCode(httpErrorCode)
                .setErrorType(null)
                .setErrorMessage(errorMessage)
                .setResponseBody(responseBody)
                .build();
    }

    /*
    Invalid requests that reach the API handlers return a JSON error object and one of the following HTTP response codes:
        400 Bad Request when parameters are missing or incorrect.
        422 Unprocessable Entity when an expression can't be executed (RFC4918).
        503 Service Unavailable when queries time out or abort.
    Other non-2xx codes may be returned for errors occurring before the API endpoint is reached.
    */
    private PrometheusResponse prometheusApiHandler(Integer responseCode, String responseBody) {
        PrometheusResponse result;
        switch (responseCode) {
            case 400:
                result = getGenericPrometheusApiError(
                        "Bad Request: Missing or incorrect parameters",
                        400,
                        responseBody
                );
                break;
            case 422:
                result = getGenericPrometheusApiError(
                        "Unprocessable Entity: Expression can't be executed",
                        422,
                        responseBody
                );
                break;
            case 503:
                result = getGenericPrometheusApiError(
                        "Service Unavailable: Query timed out or aborted",
                        503,
                        responseBody
                );
                break;
            default:
                if (responseCode >= 400 && responseCode < 600) {
                    result = getGenericPrometheusApiError(
                            "Unexpected HTTP error code " + responseCode,
                            503,
                            responseBody
                    );
                } else {
                    result = getGenericPrometheusApiError(
                            "Unhandled response code: " + responseCode,
                            503,
                            responseBody
                    );
                }
                break;
        }
        return result;
    }

    private HttpUrl prepareHttpUrl(PrometheusQueryRangeRequest prometheusQueryRangeRequest) {

        // Формирование URL
        HttpUrl url = Objects.requireNonNull(HttpUrl.get(prometheusQueryRangeRequest.getUrl()))
                .newBuilder()
                .addPathSegments("api/v1/query_range")
                .addQueryParameter("query", prometheusQueryRangeRequest.getPrometheusQuery())
                .addQueryParameter("start", String.valueOf(prometheusQueryRangeRequest.getStart().getEpochSecond()))
                .addQueryParameter("end", String.valueOf(prometheusQueryRangeRequest.getEnd().getEpochSecond()))
                .addQueryParameter("step", String.valueOf(prometheusQueryRangeRequest.getStep().getSeconds()))
                .build();

        logger.debug("Prometheus Query URL is {}", url);
        return url;

    }

    private OkHttpClient getOkHttpClient(PrometheusQueryRangeRequest prometheusQueryRangeRequest) {
        return prometheusQueryRangeRequest.getSslVerify() != null && !prometheusQueryRangeRequest.getSslVerify()
                ? getUnsafeHttpsClient()
                : getHttpsClient();
    }

    private OkHttpClient getHttpsClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .addInterceptor(new InternalHttpLoggingInterceptor()).build();
    }

    private OkHttpClient getUnsafeHttpsClient() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier((hostname, session) -> true)
                    .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                    .readTimeout(readTimeout, TimeUnit.SECONDS)
                    // !!!! log HttpLoggingInterceptor !!!! raw debug info
                    //.addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new InternalHttpLoggingInterceptor())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Failed to create unsafe OkHttpClient", e);
        }
    }
}
