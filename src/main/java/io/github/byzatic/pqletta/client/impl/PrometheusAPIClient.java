package io.github.byzatic.pqletta.client.impl;

import org.apache.commons.lang3.NotImplementedException;
import io.github.byzatic.commons.ObjectsUtils;
import io.github.byzatic.commons.base_exceptions.OperationIncompleteException;
import io.github.byzatic.pqletta.annotations.NotImplementedYet;
import io.github.byzatic.pqletta.client.PrometheusAPIClientInterface;
import io.github.byzatic.pqletta.client.dto.request.impl.PrometheusQueryRangeRequest;
import io.github.byzatic.pqletta.client.dto.response.PrometheusResponse;
import io.github.byzatic.pqletta.client.dto.response.impl.success.*;
import io.github.byzatic.pqletta.client.impl.query_range.RangeQueryExecutor;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PrometheusAPIClient implements PrometheusAPIClientInterface {
    private final RangeQueryExecutorInterface rangeQueryExecutor;
    private final URL serverUrl;
    private final Boolean sslVerify;

    public PrometheusAPIClient() {
        throw new IllegalStateException("PrometheusAPIClient should be created with Builder, PrometheusAPIClient.newBuilder()...");
    }

    private PrometheusAPIClient(Builder builder) {
        //TODO: Integer connectTimeout, Integer readTimeout to Builder
        rangeQueryExecutor = Objects.requireNonNullElse(
                builder.rangeQueryExecutor,
                new RangeQueryExecutor(10, 10)
        );
        ObjectsUtils.requireNonNull(builder.serverUrl, new IllegalArgumentException("Server URL setServerUrl(URL serverUrl) should be NotNull"));
        serverUrl = builder.serverUrl;
        ObjectsUtils.requireNonNull(builder.sslVerify, new IllegalArgumentException("SSL verify setSslVerify(Boolean sslVerify) should be NotNull"));
        sslVerify = builder.sslVerify;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(PrometheusAPIClient copy) {
        Builder builder = new Builder();
        builder.rangeQueryExecutor = copy.rangeQueryExecutor;
        builder.serverUrl = copy.serverUrl;
        builder.sslVerify = copy.sslVerify;
        return builder;
    }


    /**
     * @param promql
     * @return
     * @throws OperationIncompleteException
     */
    @NotImplementedYet
    @Override
    public PrometheusResponse query(String promql) throws OperationIncompleteException {
        throw new NotImplementedException();
    }

    /**
     * Executes a Prometheus range query with the given parameters.
     * <p>
     * This method builds a {@link PrometheusQueryRangeRequest} using the provided PromQL expression, time range,
     * and step interval, and delegates execution to the configured {@code rangeQueryExecutor}.
     *
     * @param promql the PromQL query expression to be executed
     * @param start the start timestamp (inclusive) of the query range
     * @param end the end timestamp (inclusive) of the query range
     * @param step the resolution step {@link Duration} (interval between data points in the result)
     * @return the {@link PrometheusResponse} containing the result of the query, including status and data
     * @throws OperationIncompleteException if the operation fails or the query cannot be completed
     */
    @Override
    public PrometheusResponse queryRange(String promql, Instant start, Instant end, Duration step) throws OperationIncompleteException {
        try {

            PrometheusQueryRangeRequest prometheusQueryRangeRequest = PrometheusQueryRangeRequest.newBuilder()
                    .setPrometheusQuery(promql)
                    .setStart(start)
                    .setEnd(end)
                    .setStep(step)
                    .setSslVerify(sslVerify)
                    .setUrl(serverUrl)
                    .build();

            PrometheusResponse response = rangeQueryExecutor.executePrometheusQuery(prometheusQueryRangeRequest);

            return response;

        } catch (Exception e) {
            throw new OperationIncompleteException(e);
        }
    }

    /**
     * @param matcher
     * @param start
     * @param end
     * @return
     * @throws OperationIncompleteException
     */
    @NotImplementedYet
    @Override
    public List<PrometheusResponse> getSeries(String matcher, Instant start, Instant end) throws OperationIncompleteException {
        throw new NotImplementedException();
    }

    /**
     * @param labelName
     * @return
     * @throws OperationIncompleteException
     */
    @NotImplementedYet
    @Override
    public Map<String, List<String>> getLabelValues(String labelName) throws OperationIncompleteException {
        throw new NotImplementedException();
    }

    /**
     * @return
     * @throws OperationIncompleteException
     */
    @NotImplementedYet
    @Override
    public List<PrometheusResponse> getTargets() throws OperationIncompleteException {
        throw new NotImplementedException();
    }

    /**
     * @return
     * @throws OperationIncompleteException
     */
    @NotImplementedYet
    @Override
    public List<PrometheusResponse> getAlerts() throws OperationIncompleteException {
        throw new NotImplementedException();
    }

    /**
     * @return
     * @throws OperationIncompleteException
     */
    @NotImplementedYet
    @Override
    public List<PrometheusResponse> getRules() throws OperationIncompleteException {
        throw new NotImplementedException();
    }

    /**
     * @param metricName
     * @return
     * @throws OperationIncompleteException
     */
    @NotImplementedYet
    @Override
    public List<PrometheusResponse> getMetadata(String metricName) throws OperationIncompleteException {
        throw new NotImplementedException();
    }

    /**
     * {@code PrometheusAPIClient} builder static inner class.
     */
    public static final class Builder {
        private RangeQueryExecutorInterface rangeQueryExecutor = null;
        private URL serverUrl = null;
        private Boolean sslVerify = null;

        private Builder() {
        }

        /**
         * Sets the {@code rangeQueryExecutor} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param rangeQueryExecutor the {@code rangeQueryExecutor} to set
         * @return a reference to this Builder
         */
        public Builder setRangeQueryExecutor(RangeQueryExecutorInterface rangeQueryExecutor) {
            this.rangeQueryExecutor = rangeQueryExecutor;
            return this;
        }

        /**
         * Sets the {@code serverUrl} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param serverUrl the {@code serverUrl} to set
         * @return a reference to this Builder
         */
        public Builder setServerUrl(URL serverUrl) {
            this.serverUrl = serverUrl;
            return this;
        }

        /**
         * Sets the {@code sslVerify} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param sslVerify the {@code sslVerify} to set
         * @return a reference to this Builder
         */
        public Builder setSslVerify(Boolean sslVerify) {
            this.sslVerify = sslVerify;
            return this;
        }

        /**
         * Returns a {@code PrometheusAPIClient} built from the parameters previously set.
         *
         * @return a {@code PrometheusAPIClient} built with parameters of this {@code PrometheusAPIClient.Builder}
         */
        public PrometheusAPIClient build() {
            return new PrometheusAPIClient(this);
        }
    }
}
