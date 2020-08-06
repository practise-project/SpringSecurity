package com.example.springsecurity.common.exception.handle;

import com.example.springsecurity.common.bean.ErrorResponse;
import com.example.springsecurity.common.exception.*;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

/**
 * Api调用异常处理器。
 *
 * @author TanChong
 * create date 2020\3\2 0002
 */
@ControllerAdvice
public class RestExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Value("${debug}")
    private boolean isDebugMode;


    @ExceptionHandler(value = ValidateCodeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidateCodeException(ValidateCodeException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(value = ValueInvalidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValueInvalidException(ValueInvalidException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(value = DataNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleDataNotFoundException(DataNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        return new ErrorResponse(e.getMessage());
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var error = "";
        try {
            error = e.getBindingResult().getFieldError().getDefaultMessage();
        } catch (NullPointerException nullPointerException) {
            LOGGER.error("Get Custom Error Message Failure.", nullPointerException);
            error = "系统错误";
        }

        return new ErrorResponse(error);
    }

    @ExceptionHandler(value = MismatchedInputException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMismatchedInputException(MismatchedInputException e) {
        if (!isDebugMode) {
            return new ErrorResponse("输入有误");
        } else {
            return new ErrorResponse(e.getMessage());
        }
    }

    @ExceptionHandler(value = SystemErrorException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleSystemErrorException(SystemErrorException e) {

        return new ErrorResponse("系统异常：" + e.getMessage());
    }

    @ExceptionHandler(value = RequestSuccessful.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ErrorResponse handleRequestSuccessful(RequestSuccessful e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(value = InvalidOperationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidOperationException(InvalidOperationException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(value = IOException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIOException(IOException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(Exception e) {
        return new ErrorResponse(e.getMessage());
    }
}
