package com.zuehlke.colossus.exceptions;

public class ColossusException extends RuntimeException {
    private int code;

    public ColossusException(int code) {
        this.code = code;
    }

    public ColossusException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ColossusException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public ColossusException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public ColossusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public int code() {
        return code;
    }
}
