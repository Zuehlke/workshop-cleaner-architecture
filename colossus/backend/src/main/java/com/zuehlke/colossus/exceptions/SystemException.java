package com.zuehlke.colossus.exceptions;

public class SystemException extends ColossusException {

    public SystemException(String message, int code) {
        super(message, code);
    }

    public SystemException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

}
