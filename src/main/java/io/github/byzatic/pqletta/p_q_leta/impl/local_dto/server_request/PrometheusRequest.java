package io.github.byzatic.pqletta.p_q_leta.impl.local_dto.server_request;

import java.util.Objects;

public class PrometheusRequest {
    private String PrometheusQueryId;

    private PrometheusRequest() {
    }

    private PrometheusRequest(Builder builder) {
        PrometheusQueryId = builder.PrometheusQueryId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(PrometheusRequest copy) {
        Builder builder = new Builder();
        builder.PrometheusQueryId = copy.getPrometheusQueryId();
        return builder;
    }

    public String getPrometheusQueryId() {
        return PrometheusQueryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrometheusRequest that = (PrometheusRequest) o;
        return Objects.equals(PrometheusQueryId, that.PrometheusQueryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(PrometheusQueryId);
    }

    @Override
    public String toString() {
        return "PrometheusQueryRequest{" +
                "PrometheusQueryId='" + PrometheusQueryId + '\'' +
                '}';
    }

    /**
     * {@code PrometheusQueryRequest} builder static inner class.
     */
    public static final class Builder {
        private String PrometheusQueryId;

        private Builder() {
        }

        /**
         * Sets the {@code PrometheusQueryId} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param PrometheusQueryId the {@code PrometheusQueryId} to set
         * @return a reference to this Builder
         */
        public Builder setPrometheusQueryId(String PrometheusQueryId) {
            this.PrometheusQueryId = PrometheusQueryId;
            return this;
        }

        /**
         * Returns a {@code PrometheusQueryRequest} built from the parameters previously set.
         *
         * @return a {@code PrometheusQueryRequest} built with parameters of this {@code PrometheusQueryRequest.Builder}
         */
        public PrometheusRequest build() {
            return new PrometheusRequest(this);
        }
    }
}
