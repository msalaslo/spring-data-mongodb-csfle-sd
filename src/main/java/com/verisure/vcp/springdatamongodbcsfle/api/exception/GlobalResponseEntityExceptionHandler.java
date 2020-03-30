package com.verisure.vcp.springdatamongodbcsfle.api.exception;

import com.verisure.vcp.springdatamongodbcsfle.api.dto.ErrorDTO;
import com.verisure.vcp.springdatamongodbcsfle.service.exception.ServiceException;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Generic error handling mechanism.
 *
 * @since 1.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
@ControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler  {

    @ExceptionHandler(value = { ServiceException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleException(RuntimeException throwable, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorDTO errorDto = ErrorDTO.builder()
                .timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .status(httpStatus.value()) // assuming HttpStatus param.
                .error(httpStatus.getReasonPhrase())
                .exception(throwable.getClass().getName()) // assuming Throwable param.
                .message(throwable.getMessage())
                .build();
        return handleExceptionInternal(throwable, errorDto, new HttpHeaders(), httpStatus, request);
    }
 }
