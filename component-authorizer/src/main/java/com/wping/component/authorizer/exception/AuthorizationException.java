package com.wping.component.authorizer.exception;

/**
 * describe:
 *
 * @author Wping.gao
 * @date 2018/12/03
 */
public class AuthorizationException extends RuntimeException {
    private static final long serialVersionUID = 8487435361243016636L;

    public AuthorizationException() {
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationException(Throwable cause) {
        super(cause);
    }

    public AuthorizationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
