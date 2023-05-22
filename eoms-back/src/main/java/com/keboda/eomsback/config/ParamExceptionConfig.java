package com.keboda.eomsback.config;


import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.keboda.eomsback.entity.BaseResult;
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
        exception.printStackTrace();
        return BaseResult.fail(exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResult handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return BaseResult.fail("错误:"+e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResult handleException(Exception e) {
        e.printStackTrace();
        return BaseResult.fail("系统异常:"+e.getMessage());
    }

    @ExceptionHandler({SignatureVerificationException.class,TokenExpiredException.class, AlgorithmMismatchException.class, JWTDecodeException.class})
    public BaseResult handleTokenException(Exception e) {
        e.printStackTrace();
        BaseResult fail =  BaseResult.fail("token验证错误:"+e.getMessage());
        fail.setStatus(403);
        return fail;
    }
}
