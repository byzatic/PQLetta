package io.github.byzatic.pqletta.client.dto.response.impl.error;

import io.github.byzatic.pqletta.client.dto.response.PrometheusApiError;

import java.util.Objects;

public class GenericPrometheusApiError implements PrometheusApiError {
    private Integer HttpStatusCode;
    private String errorType;
    private String errorMessage;
    private String responseBody;

    public GenericPrometheusApiError() {
    }

    private GenericPrometheusApiError(Builder builder) {
        HttpStatusCode = builder.HttpStatusCode;
        errorType = builder.errorType;
        errorMessage = builder.errorMessage;
        responseBody = builder.responseBody;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(GenericPrometheusApiError copy) {
        Builder builder = new Builder();
        builder.HttpStatusCode = copy.getHttpStatusCode();
        builder.errorType = copy.getErrorType();
        builder.errorMessage = copy.getErrorMessage();
        builder.responseBody = copy.getResponseBody();
        return builder;
    }

    public Integer getHttpStatusCode() {
        return HttpStatusCode;
    }

    public String getErrorType() {
        return errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getResponseBody() {
        return responseBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericPrometheusApiError that = (GenericPrometheusApiError) o;
        return Objects.equals(HttpStatusCode, that.HttpStatusCode) && Objects.equals(errorType, that.errorType) && Objects.equals(errorMessage, that.errorMessage) && Objects.equals(responseBody, that.responseBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(HttpStatusCode, errorType, errorMessage, responseBody);
    }

    @Override
    public String toString() {
        return "GenericPrometheusApiError{" +
                "HttpStatusCode=" + HttpStatusCode +
                ", errorType='" + errorType + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", responseBody='" + responseBody + '\'' +
                '}';
    }

    /**
     * {@code GenericPrometheusApiError} builder static inner class.
     */
    public static final class Builder {
        private Integer HttpStatusCode;
        private String errorType;
        private String errorMessage;
        private String responseBody;

        private Builder() {
        }

        /**
         * Sets the {@code HttpStatusCode} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param HttpStatusCode the {@code HttpStatusCode} to set
         * @return a reference to this Builder
         */
        public Builder setHttpStatusCode(Integer HttpStatusCode) {
            this.HttpStatusCode = HttpStatusCode;
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
         * Sets the {@code errorMessage} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param errorMessage the {@code errorMessage} to set
         * @return a reference to this Builder
         */
        public Builder setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        /**
         * Sets the {@code responseBody} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param responseBody the {@code responseBody} to set
         * @return a reference to this Builder
         */
        public Builder setResponseBody(String responseBody) {
            this.responseBody = responseBody;
            return this;
        }

        /**
         * Returns a {@code GenericPrometheusApiError} built from the parameters previously set.
         *
         * @return a {@code GenericPrometheusApiError} built with parameters of this {@code GenericPrometheusApiError.Builder}
         */
        public GenericPrometheusApiError build() {
            return new GenericPrometheusApiError(this);
        }
    }
}
