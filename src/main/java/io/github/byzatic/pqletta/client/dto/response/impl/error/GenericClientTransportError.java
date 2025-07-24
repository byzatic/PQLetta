package io.github.byzatic.pqletta.client.dto.response.impl.error;

import io.github.byzatic.pqletta.client.dto.response.ClientTransportError;

import java.util.Objects;

public class GenericClientTransportError implements ClientTransportError {
    private String message;
    private Throwable cause;

    public GenericClientTransportError() {
    }

    private GenericClientTransportError(Builder builder) {
        message = builder.message;
        cause = builder.cause;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(GenericClientTransportError copy) {
        Builder builder = new Builder();
        builder.message = copy.getMessage();
        builder.cause = copy.getCause();
        return builder;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getCause() {
        return cause;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericClientTransportError that = (GenericClientTransportError) o;
        return Objects.equals(message, that.message) && Objects.equals(cause, that.cause);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, cause);
    }

    @Override
    public String toString() {
        return "GenericClientTransportError{" +
                "message='" + message + '\'' +
                ", cause=" + cause +
                '}';
    }

    /**
     * {@code GenericClientTransportError} builder static inner class.
     */
    public static final class Builder {
        private String message;
        private Throwable cause;

        private Builder() {
        }

        /**
         * Sets the {@code message} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param message the {@code message} to set
         * @return a reference to this Builder
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Sets the {@code cause} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param cause the {@code cause} to set
         * @return a reference to this Builder
         */
        public Builder setCause(Throwable cause) {
            this.cause = cause;
            return this;
        }

        /**
         * Returns a {@code GenericClientTransportError} built from the parameters previously set.
         *
         * @return a {@code GenericClientTransportError} built with parameters of this {@code GenericClientTransportError.Builder}
         */
        public GenericClientTransportError build() {
            return new GenericClientTransportError(this);
        }
    }
}
