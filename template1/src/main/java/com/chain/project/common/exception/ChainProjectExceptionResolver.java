package com.chain.project.common.exception;

import com.chain.project.common.directory.Constant;
import com.chain.project.common.domain.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一处理所有的异常（但不包括状态码处理，由ResponseStatusExceptionResolver处理）并做log
 */
@ControllerAdvice
public class ChainProjectExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(ChainProjectExceptionResolver.class);

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result exception(Exception e) {
        logger.error("===== !!! [EXCEPTION] !!! =====", e);
        String data = Constant.NULL;
        //默认返回的是加密的错误消息
        return Result.fail(data, Result.FAILURE);
    }
}
