package io.github.byzatic.pqletta.p_q_leta.impl.local_dto.query_configuration;

import java.util.List;
import java.util.Objects;

public class PrometheusQueryConfiguration {
    private String serverURL = null;
    private String serverSSLVerify = null;
    private String queryType = null;
    private String queryIdentifier = null;
    private String queryStep = null;
    private String queryTimeRange = null;
    private List<QueryParameter> queryParameters = null;
    private List<QueryLabel> queryLabels = null;

    private PrometheusQueryConfiguration() {
    }

    private PrometheusQueryConfiguration(Builder builder) {
        serverURL = builder.serverURL;
        serverSSLVerify = builder.serverSSLVerify;
        queryType = builder.queryType;
        queryIdentifier = builder.queryIdentifier;
        queryStep = builder.queryStep;
        queryTimeRange = builder.queryTimeRange;
        queryParameters = builder.queryParameters;
        queryLabels = builder.queryLabels;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(PrometheusQueryConfiguration copy) {
        Builder builder = new Builder();
        builder.serverURL = copy.getServerURL();
        builder.serverSSLVerify = copy.getServerSSLVerify();
        builder.queryType = copy.getQueryType();
        builder.queryIdentifier = copy.getQueryIdentifier();
        builder.queryStep = copy.getQueryStep();
        builder.queryTimeRange = copy.getQueryTimeRange();
        builder.queryParameters = copy.getQueryParameters();
        builder.queryLabels = copy.getQueryLabels();
        return builder;
    }

    public String getServerURL() {
        return serverURL;
    }

    public String getServerSSLVerify() {
        return serverSSLVerify;
    }

    public String getQueryType() {
        return queryType;
    }

    public String getQueryIdentifier() {
        return queryIdentifier;
    }

    public String getQueryStep() {
        return queryStep;
    }

    public String getQueryTimeRange() {
        return queryTimeRange;
    }

    public List<QueryParameter> getQueryParameters() {
        return queryParameters;
    }

    public List<QueryLabel> getQueryLabels() {
        return queryLabels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrometheusQueryConfiguration that = (PrometheusQueryConfiguration) o;
        return Objects.equals(serverURL, that.serverURL) && Objects.equals(serverSSLVerify, that.serverSSLVerify) && Objects.equals(queryType, that.queryType) && Objects.equals(queryIdentifier, that.queryIdentifier) && Objects.equals(queryStep, that.queryStep) && Objects.equals(queryTimeRange, that.queryTimeRange) && Objects.equals(queryParameters, that.queryParameters) && Objects.equals(queryLabels, that.queryLabels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverURL, serverSSLVerify, queryType, queryIdentifier, queryStep, queryTimeRange, queryParameters, queryLabels);
    }

    @Override
    public String toString() {
        return "PrometheusQuery{" +
                "serverURL='" + serverURL + '\'' +
                ", serverSSLVerify='" + serverSSLVerify + '\'' +
                ", queryType='" + queryType + '\'' +
                ", queryIdentifier='" + queryIdentifier + '\'' +
                ", queryStep='" + queryStep + '\'' +
                ", queryTimeRange='" + queryTimeRange + '\'' +
                ", queryParameters=" + queryParameters +
                ", queryLabels=" + queryLabels +
                '}';
    }

    /**
     * {@code PrometheusQuery} builder static inner class.
     */
    public static final class Builder {
        private String serverURL;
        private String serverSSLVerify;
        private String queryType;
        private String queryIdentifier;
        private String queryStep;
        private String queryTimeRange;
        private List<QueryParameter> queryParameters;
        private List<QueryLabel> queryLabels;

        private Builder() {
        }

        /**
         * Sets the {@code serverURL} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param serverURL the {@code serverURL} to set
         * @return a reference to this Builder
         */
        public Builder setServerURL(String serverURL) {
            this.serverURL = serverURL;
            return this;
        }

        /**
         * Sets the {@code serverSSLVerify} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param serverSSLVerify the {@code serverSSLVerify} to set
         * @return a reference to this Builder
         */
        public Builder setServerSSLVerify(String serverSSLVerify) {
            this.serverSSLVerify = serverSSLVerify;
            return this;
        }

        /**
         * Sets the {@code queryType} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param queryType the {@code queryType} to set
         * @return a reference to this Builder
         */
        public Builder setQueryType(String queryType) {
            this.queryType = queryType;
            return this;
        }

        /**
         * Sets the {@code queryIdentifier} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param queryIdentifier the {@code queryIdentifier} to set
         * @return a reference to this Builder
         */
        public Builder setQueryIdentifier(String queryIdentifier) {
            this.queryIdentifier = queryIdentifier;
            return this;
        }

        /**
         * Sets the {@code queryStep} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param queryStep the {@code queryStep} to set
         * @return a reference to this Builder
         */
        public Builder setQueryStep(String queryStep) {
            this.queryStep = queryStep;
            return this;
        }

        /**
         * Sets the {@code queryTimeRange} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param queryTimeRange the {@code queryTimeRange} to set
         * @return a reference to this Builder
         */
        public Builder setQueryTimeRange(String queryTimeRange) {
            this.queryTimeRange = queryTimeRange;
            return this;
        }

        /**
         * Sets the {@code queryParameters} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param queryParameters the {@code queryParameters} to set
         * @return a reference to this Builder
         */
        public Builder setQueryParameters(List<QueryParameter> queryParameters) {
            this.queryParameters = queryParameters;
            return this;
        }

        /**
         * Sets the {@code queryLabels} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param queryLabels the {@code queryLabels} to set
         * @return a reference to this Builder
         */
        public Builder setQueryLabels(List<QueryLabel> queryLabels) {
            this.queryLabels = queryLabels;
            return this;
        }

        /**
         * Returns a {@code PrometheusQuery} built from the parameters previously set.
         *
         * @return a {@code PrometheusQuery} built with parameters of this {@code PrometheusQuery.Builder}
         */
        public PrometheusQueryConfiguration build() {
            return new PrometheusQueryConfiguration(this);
        }
    }
}
