package com.lblaga.demostore.transfer;

/**
 * Error json containing HTTP error code and message
 */
public final class ErrorJson {
    private final int code;
    private final String message;

    public ErrorJson(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
