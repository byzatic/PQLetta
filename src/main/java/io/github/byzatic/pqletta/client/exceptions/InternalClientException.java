package io.github.byzatic.pqletta.client.exceptions;

import io.github.byzatic.commons.base_exceptions.OperationIncompleteException;

public class InternalClientException extends OperationIncompleteException {

    public InternalClientException(String message) {
        super(message);
    }

    public InternalClientException(Throwable cause) {
        super(cause);
    }

    public InternalClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalClientException(Throwable cause, String message) {
        super(cause, message);
    }
}
