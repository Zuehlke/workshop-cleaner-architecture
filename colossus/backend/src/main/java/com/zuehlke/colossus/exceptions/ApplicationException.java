package com.zuehlke.colossus.exceptions;

public class ApplicationException extends ColossusException {

    public final static int UNAUTHORIZED = 1001;
    public final static int NOT_FOUND = 1002;

    public ApplicationException(String message, int code) {
        super(message, code);
    }

    public ApplicationException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

}
