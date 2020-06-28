package com.webank.staging.common.exception.handler;

import java.util.List;

import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.webank.staging.common.exception.BusinessException;
import com.webank.staging.common.exception.code.BaseResponseCode;
import com.webank.staging.common.utils.DataResult;

/**
 * RestExceptionHandler
 */
@RestControllerAdvice
public class RestExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);
	
    /**
     * 系统繁忙，请稍候再试"
     */
    @ExceptionHandler(Exception.class)
    public <T> DataResult<T> handleException(Exception e) {
        log.error("Exception,exception:{}", e);
        return DataResult.getResult(BaseResponseCode.SYSTEM_BUSY);
    }

    /**
     * 自定义全局异常处理
     */
    @ExceptionHandler(value = BusinessException.class)
    <T> DataResult<T> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException,exception:{}", e);
        return new DataResult<>(e.getMessageCode(), e.getDetailMessage());
    }

//    /**
//     * 没有权限 返回403视图
//     */
//    @ExceptionHandler(value = AuthorizationException.class)
//    public <T> DataResult<T> errorPermission(AuthorizationException e) {
//        log.error("BusinessException,exception:{}", e);
//        return new DataResult<>(BaseResponseCode.UNAUTHORIZED_ERROR);
//
//    }

    /**
     * 处理validation 框架异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    <T> DataResult<T> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("methodArgumentNotValidExceptionHandler bindingResult.allErrors():{},exception:{}", e.getBindingResult().getAllErrors(), e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        return DataResult.getResult(BaseResponseCode.METHODARGUMENTNOTVALIDEXCEPTION.getCode(), errors.get(0).getDefaultMessage());
    }

}
