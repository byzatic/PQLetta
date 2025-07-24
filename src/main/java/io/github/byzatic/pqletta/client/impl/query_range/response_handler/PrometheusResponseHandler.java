package io.github.byzatic.pqletta.client.impl.query_range.response_handler;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.byzatic.pqletta.client.dto.response.PrometheusResponse;
import io.github.byzatic.pqletta.client.dto.response.impl.error.GenericPrometheusApiError;
import io.github.byzatic.pqletta.client.dto.response.impl.success.PrometheusResult;
import io.github.byzatic.pqletta.client.exceptions.InternalClientException;
import io.github.byzatic.pqletta.client.impl.query_range.PrometheusResponseHandlerInterface;
import io.github.byzatic.pqletta.client.impl.query_range.response_handler.dto.Response;

public class PrometheusResponseHandler implements PrometheusResponseHandlerInterface {
    private final static Logger logger = LoggerFactory.getLogger(PrometheusResponseHandler.class);

    public PrometheusResponseHandler() {
    }

    @Override
    @NotNull
    public PrometheusResponse processResponse(@NotNull String responseBodyJson, @NotNull Integer httpStatusCode) throws InternalClientException {
        try {
            logger.debug("httpStatusCode -> {} responseBodyJson -> {}", httpStatusCode, responseBodyJson);

            String copyOfResponseBodyJson = String.copyValueOf(responseBodyJson.toCharArray());
            logger.trace("Response body copied");

            Response serverResponse = unmarshalResponse(copyOfResponseBodyJson);
            logger.trace("Response body unmarshalled to Response");

            PrometheusResponse response = processStatusBasedLogic(serverResponse, httpStatusCode);
            logger.trace("Response processed to PrometheusResponse");

            logger.debug("Returns instance of PrometheusResponse -> {}", response);
            return response;
        } catch (Exception e) {
            throw new InternalClientException(e);
        }
    }

    @NotNull
    private PrometheusResponse processStatusBasedLogic(@NotNull Response serverResponse, @NotNull Integer httpStatusCode) throws InternalClientException {
        try {
            PrometheusResponse response = switch (serverResponse.getStatus()) {
                case "success" -> PrometheusResult.newBuilder()
                        .setStatus(serverResponse.getStatus())
                        .setData(SupportDataConverter.convert(serverResponse.getData()))
                        .setInfos(serverResponse.getInfos())
                        .setWarnings(serverResponse.getWarnings())
                        .build();
                case "error" -> GenericPrometheusApiError.newBuilder()
                        .setErrorMessage(serverResponse.getError())
                        .setErrorType(serverResponse.getErrorType())
                        .setHttpStatusCode(httpStatusCode)
                        .build();
                default -> {
                    String errorMessage = "Not valid status" + serverResponse.getStatus();
                    throw new IllegalArgumentException(errorMessage);
                }
            };

            logger.debug("Processed response is {}", response);
            return response;
        } catch (Exception e) {
            throw new InternalClientException(e);
        }
    }

    @NotNull
    private Response unmarshalResponse(@NotNull String responseBodyJson) throws InternalClientException {
        try {
            Response response = SupportPrometheusResponseUnmarshaller.get(responseBodyJson);

            logger.debug("Unmarshalled response is {}", response);
            return response;
        } catch (Exception e) {
            throw new InternalClientException(e);
        }
    }
}
