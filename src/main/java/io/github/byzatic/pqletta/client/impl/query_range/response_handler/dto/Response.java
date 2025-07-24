package io.github.byzatic.pqletta.client.impl.query_range.response_handler.dto;

import java.util.List;
import java.util.Objects;

public class Response {
    private String status = null;
    private Data data = null;

    // Only set if status is "error". The data field may still hold
    // additional data.
    private String errorType = null;
    private String error = null;

    // Only set if there were warnings while executing the request.
    // There will still be data in the data field.
    private List<String> warnings = null;

    // Only set if there were info-level annotations while executing the request.
    private List<String> infos = null;

    public Response() {
    }

    private Response(Builder builder) {
        status = builder.status;
        data = builder.data;
        errorType = builder.errorType;
        error = builder.error;
        warnings = builder.warnings;
        infos = builder.infos;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(Response copy) {
        Builder builder = new Builder();
        builder.status = copy.getStatus();
        builder.data = copy.getData();
        builder.errorType = copy.getErrorType();
        builder.error = copy.getError();
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

    public String getErrorType() {
        return errorType;
    }

    public String getError() {
        return error;
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
        Response response = (Response) o;
        return Objects.equals(status, response.status) && Objects.equals(data, response.data) && Objects.equals(errorType, response.errorType) && Objects.equals(error, response.error) && Objects.equals(warnings, response.warnings) && Objects.equals(infos, response.infos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, data, errorType, error, warnings, infos);
    }

    @Override
    public String toString() {
        return "PrometheusServerResponse{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", errorType='" + errorType + '\'' +
                ", error='" + error + '\'' +
                ", warnings=" + warnings +
                ", infos=" + infos +
                '}';
    }

    /**
     * {@code PrometheusServerResponse} builder static inner class.
     */
    public static final class Builder {
        private String status;
        private Data data;
        private String errorType;
        private String error;
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
         * Sets the {@code errorType} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param errorType the {@code errorType} to set
         * @return a reference to this Builder
         */
        public Builder setErrorType(String errorType) {
            this.errorType = errorType;
            return this;
        }

        /**
         * Sets the {@code error} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param error the {@code error} to set
         * @return a reference to this Builder
         */
        public Builder setError(String error) {
            this.error = error;
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
         * Returns a {@code PrometheusServerResponse} built from the parameters previously set.
         *
         * @return a {@code PrometheusServerResponse} built with parameters of this {@code PrometheusServerResponse.Builder}
         */
        public Response build() {
            return new Response(this);
        }
    }
}
