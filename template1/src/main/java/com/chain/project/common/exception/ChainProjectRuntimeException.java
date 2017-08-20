package com.chain.project.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainProjectRuntimeException extends RuntimeException {

    private static Logger logger = LoggerFactory.getLogger(ChainProjectRuntimeException.class);

    public ChainProjectRuntimeException() {
        super();
        logger.error("car server runtime exception");
    }

    public ChainProjectRuntimeException(String message) {
        super(message);
        logger.error("car server runtime exception: " + message);
    }

    public ChainProjectRuntimeException(String message, Throwable cause) {
        super(message, cause);
        logger.error("car server runtime exception: " + message, cause);
    }

    public ChainProjectRuntimeException(Throwable cause) {
        super(cause);
        logger.error("car server runtime exception", cause);
    }

    protected ChainProjectRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error("car server runtime exception: " + message, cause);
    }
}
