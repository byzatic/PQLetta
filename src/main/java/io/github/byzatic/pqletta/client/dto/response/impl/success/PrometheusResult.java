package io.github.byzatic.pqletta.client.dto.response.impl.success;

import io.github.byzatic.pqletta.client.dto.response.ResponseSuccess;

import java.util.List;
import java.util.Objects;

public class PrometheusResult implements ResponseSuccess {
    private String status;
    private Data data = null;
    private List<String> warnings;
    private List<String> infos;

    public PrometheusResult() {
    }

    private PrometheusResult(Builder builder) {
        status = builder.status;
        data = builder.data;
        warnings = builder.warnings;
        infos = builder.infos;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(PrometheusResult copy) {
        Builder builder = new Builder();
        builder.status = copy.getStatus();
        builder.data = copy.getData();
        builder.warnings = copy.getWarnings();
        builder.infos = copy.getInfos();
        return builder;
    }

    public String getStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public List<String> getInfos() {
        return infos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrometheusResult prometheusServerSuccessResponse = (PrometheusResult) o;
        return Objects.equals(status, prometheusServerSuccessResponse.status) && Objects.equals(data, prometheusServerSuccessResponse.data) && Objects.equals(warnings, prometheusServerSuccessResponse.warnings) && Objects.equals(infos, prometheusServerSuccessResponse.infos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, data, warnings, infos);
    }

    @Override
    public String toString() {
        return "Success{" +
                "status='" + status + '\'' +
                ", data='" + data + '\'' +
                ", warnings=" + warnings +
                ", infos=" + infos +
                '}';
    }

    /**
     * {@code Success} builder static inner class.
     */
    public static final class Builder {
        private String status;
        private Data data;
        private List<String> warnings;
        private List<String> infos;

        private Builder() {
        }

        /**
         * Sets the {@code status} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param status the {@code status} to set
         * @return a reference to this Builder
         */
        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        /**
         * Sets the {@code data} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param data the {@code data} to set
         * @return a reference to this Builder
         */
        public Builder setData(Data data) {
            this.data = data;
            return this;
        }

        /**
         * Sets the {@code warnings} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param warnings the {@code warnings} to set
         * @return a reference to this Builder
         */
        public Builder setWarnings(List<String> warnings) {
            this.warnings = warnings;
            return this;
        }

        /**
         * Sets the {@code infos} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param infos the {@code infos} to set
         * @return a reference to this Builder
         */
        public Builder setInfos(List<String> infos) {
            this.infos = infos;
            return this;
        }

        /**
         * Returns a {@code Success} built from the parameters previously set.
         *
         * @return a {@code Success} built with parameters of this {@code Success.Builder}
         */
        public PrometheusResult build() {
            return new PrometheusResult(this);
        }
    }
}
