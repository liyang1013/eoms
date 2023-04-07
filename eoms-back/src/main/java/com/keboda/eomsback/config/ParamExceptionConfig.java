package com.keboda.eomsback.config;


import com.keboda.eomsback.common.BaseResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕捉
 */
@RestControllerAdvice
public class ParamExceptionConfig {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResult methodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
        return BaseResult.fail(exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResult handleRuntimeException(RuntimeException e) {
        return BaseResult.fail("系统异常:"+e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResult handleException(Exception e) {
        return BaseResult.fail("系统异常:"+e.getMessage());
    }
}
