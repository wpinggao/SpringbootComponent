package com.wping.component.redis.exception;

public class RedisDistributeLockException extends RuntimeException {

    private static final long serialVersionUID = -6565855118944961079L;

    public RedisDistributeLockException(String message) {
        super(message);
    }

    public RedisDistributeLockException(String message, Throwable cause) {
        super(message, cause);
    }

}
