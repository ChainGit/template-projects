package com.chain.project.common.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainProjectException extends Exception {

    private static Logger logger = LoggerFactory.getLogger(ChainProjectException.class);

    public ChainProjectException() {
        super();
        logger.error("car server exception");
    }

    public ChainProjectException(String message) {
        super(message);
        logger.error("car server exception: " + message);
    }

    public ChainProjectException(String message, Throwable cause) {
        super(message, cause);
        logger.error("car server exception: " + message, cause);
    }

    public ChainProjectException(Throwable cause) {
        super(cause);
        logger.error("car server exception", cause);
    }

    protected ChainProjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error("car server exception: " + message, cause);
    }
}
