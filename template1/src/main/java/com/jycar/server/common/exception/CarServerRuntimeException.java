package com.jycar.server.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarServerRuntimeException extends RuntimeException {

    private static Logger logger = LoggerFactory.getLogger(CarServerRuntimeException.class);

    public CarServerRuntimeException() {
        super();
        logger.error("car server runtime exception");
    }

    public CarServerRuntimeException(String message) {
        super(message);
        logger.error("car server runtime exception: " + message);
    }

    public CarServerRuntimeException(String message, Throwable cause) {
        super(message, cause);
        logger.error("car server runtime exception: " + message, cause);
    }

    public CarServerRuntimeException(Throwable cause) {
        super(cause);
        logger.error("car server runtime exception", cause);
    }

    protected CarServerRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error("car server runtime exception: " + message, cause);
    }
}
