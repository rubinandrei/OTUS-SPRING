package org.booklibrary.exception;


public class ObjectFoundException extends RuntimeException {

    public ObjectFoundException(String message) {
        super(message);
    }

    public ObjectFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectFoundException(Throwable cause) {
        super(cause);
    }
}