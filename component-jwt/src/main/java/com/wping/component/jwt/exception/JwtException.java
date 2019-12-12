package com.wping.component.jwt.exception;

public class JwtException extends RuntimeException {
    private static final long serialVersionUID = -3760524526493913977L;

    public JwtException(String message) {
        super(message);
    }

    public JwtException(String message, Throwable cause) {
        super(message, cause);
    }

}
