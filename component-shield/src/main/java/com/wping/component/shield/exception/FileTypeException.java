package com.wping.component.shield.exception;

/**
 * describe:
 *
 * @author Wping.gao
 * @date 2018/12/03
 */
public class FileTypeException extends RuntimeException {
    private static final long serialVersionUID = 8487435361243016636L;

    public FileTypeException() {
    }

    public FileTypeException(String message) {
        super(message);
    }

    public FileTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileTypeException(Throwable cause) {
        super(cause);
    }

    public FileTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
