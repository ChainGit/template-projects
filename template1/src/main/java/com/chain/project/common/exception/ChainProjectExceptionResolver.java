package com.chain.project.common.exception;

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
        //手动回滚的异常处理，只是简单的打印信息
        if (e instanceof DoRollBack) {
            logger.info("do rollback: " + e.getMessage());
            // TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail("错误: " + ErrorCode.ROLL_BACK + ",原因: ["
                    + ErrorCode.getErrorMsg(ErrorCode.ROLL_BACK) + "] " + e.getMessage(), Result.ERROR);
        } else {
            int errorCode = ErrorCode.DEFAULT;
            if (e instanceof ChainProjectException)
                errorCode = ((ChainProjectException) e).getErrorCode();
            else if (e instanceof ChainProjectRuntimeException)
                errorCode = ((ChainProjectRuntimeException) e).getErrorCode();
            logger.error("===== !!! [EXCEPTION] !!! =====", e);
            //默认返回的是加密的错误结果Result
            return Result.fail("错误: " + errorCode + " ["
                    + ErrorCode.getErrorMsg(errorCode) + "] " + ",请联系管理员", Result.ERROR);
        }
    }

}
