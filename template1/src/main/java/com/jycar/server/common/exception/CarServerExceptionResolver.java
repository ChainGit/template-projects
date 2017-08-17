package com.jycar.server.common.exception;

import com.jycar.server.common.directory.Constant;
import com.jycar.server.common.domain.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一处理所有的异常（但不包括状态码处理，由ResponseStatusExceptionResolver处理）并做log
 */
@ControllerAdvice
public class CarServerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(CarServerExceptionResolver.class);

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result exception(Exception e) {
        logger.error("---> [exception] ", e);
        String data = Constant.NULL;
        //默认返回的是加密的错误消息
        return Result.fail(data, Result.FAILURE);
    }
}
