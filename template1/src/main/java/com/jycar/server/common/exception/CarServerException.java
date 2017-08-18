package com.jycar.server.common.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarServerException extends Exception {

    private static Logger logger = LoggerFactory.getLogger(CarServerException.class);

    public CarServerException() {
        super();
        logger.error("car server exception");
    }

    public CarServerException(String message) {
        super(message);
        logger.error("car server exception: " + message);
    }

    public CarServerException(String message, Throwable cause) {
        super(message, cause);
        logger.error("car server exception: " + message, cause);
    }

    public CarServerException(Throwable cause) {
        super(cause);
        logger.error("car server exception", cause);
    }

    protected CarServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error("car server exception: " + message, cause);
    }
}
