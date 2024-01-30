package com.fintech.transaction.exceptions;

import com.fintech.transaction.enums.ErrorCodes;
import com.fintech.transaction.utils.RequestUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ErrorHandler {

    MessageSource messageSource;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotFoundException(BaseException ex){
        var traceId = UUID.randomUUID().toString();
        var timestamp = LocalDateTime.now();
        log.error("Exception occurred, traceId {}, message {}, code {}, timestamp {}",
                traceId, ex.getMessage(), ex.getCode(), timestamp);
        return ErrorResponse.builder()
                .traceId(traceId)
                .description(ex.getMessage())
                .errorCode(ex.getCode())
                .path(RequestUtil.getRequestPath())
                .time(timestamp)
                .build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<ValueError> errors = fieldErrors.stream()
                .map(this::buildValueError)
                .collect(Collectors.toList());

        var traceId = UUID.randomUUID().toString();
        var timestamp = LocalDateTime.now();
        log.error("Exception occurred, traceId {}, message {}, code {}, timestamp {}",
                traceId, "Invalid Arguments", ErrorCodes.INVALID_ARGUMENTS.getCode(), timestamp);

        return ErrorResponse.builder()
                .traceId(traceId)
                .description("Invalid Arguments!")
                .errorCode(ErrorCodes.INVALID_ARGUMENTS.getCode())
                .path(RequestUtil.getRequestPath())
                .time(timestamp)
                .errors(errors)
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception ex) {
        var traceId = UUID.randomUUID().toString();
        var time = LocalDateTime.now();
        log.error("Exception occurred, traceId {}, code {}, time {}",
                traceId + ex.getMessage() + ErrorCodes.INTERNAL_SERVER_ERROR.getCode() + time);
        return ErrorResponse.builder()
                .traceId(traceId)
                .description(ErrorCodes.INTERNAL_SERVER_ERROR.getMessage())
                .errorCode(ErrorCodes.INTERNAL_SERVER_ERROR.getCode())
                .path(RequestUtil.getRequestPath())
                .time(time)
                .build();
    }


    private ValueError buildValueError(ConstraintViolation<?> violation) {
        PathImpl propertyPath = (PathImpl) violation.getPropertyPath();
        Path.Node node = propertyPath.getLeafNode();
        String name = node.getName();
        return ValueError.builder()
                .message(getErrorMessage(violation.getMessage()))
                .path(violation.getPropertyPath().toString())
                .property(name)
                .build();
    }

    private ValueError buildValueError(FieldError error) {
        return ValueError.builder()
                .message(getErrorMessage(error.getDefaultMessage()))
                .path(error.getObjectName().concat(".").concat(error.getField()))
                .property(error.getField())
                .build();
    }
    private String getErrorMessage(String key) {
        try {
            return messageSource.getMessage(key, null, Locale.ENGLISH);
        } catch (Exception ex) {
            return key;
        }
    }

}


