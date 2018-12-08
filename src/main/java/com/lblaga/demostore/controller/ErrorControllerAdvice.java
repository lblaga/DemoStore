package com.lblaga.demostore.controller;

import com.lblaga.demostore.exception.OrderNotFoundException;
import com.lblaga.demostore.exception.ProductNotFoundException;
import com.lblaga.demostore.transfer.ErrorJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * Generic Error handler.
 */
@RestControllerAdvice
public class ErrorControllerAdvice {
    private static final Logger LOG = LoggerFactory.getLogger(ErrorControllerAdvice.class);

    @ExceptionHandler({ProductNotFoundException.class, OrderNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorJson notFound(final Exception exception) {
        logException(exception);
        return new ErrorJson(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorJson illegalArgumentException(final IllegalArgumentException exception) {
        logException(exception);
        return new ErrorJson(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorJson constraintViolationException(final DataIntegrityViolationException exception) {
        logException(exception);
        return new ErrorJson(HttpStatus.BAD_REQUEST.value(), exception.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(TransactionSystemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorJson transactionSystemExceptionException(final TransactionSystemException exception) {
        logException(exception);
        Throwable mostSpecificCause = exception.getMostSpecificCause();
        String message;
        if (mostSpecificCause instanceof ConstraintViolationException) {
            message = ((ConstraintViolationException) mostSpecificCause).getConstraintViolations()
                    .stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("; "));
        } else {
            message = mostSpecificCause.getMessage();
        }
        return new ErrorJson(HttpStatus.BAD_REQUEST.value(), message);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorJson validateException(final Exception exception) {
        logException(exception);
        String details = "";
        if (exception instanceof MethodArgumentNotValidException) {
            details = "Details: " + ((MethodArgumentNotValidException) exception).getBindingResult().getAllErrors()
                    .stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", "));
        } else if (exception.getCause() != null) {
            Throwable cause = exception.getCause();
            // get root cause
            while (cause != null) {
                if (cause.getMessage() != null) {
                    details = cause.getMessage();
                }
                cause = cause.getCause();
            }

        }
        return new ErrorJson(HttpStatus.BAD_REQUEST.value(), "Invalid request JSON data. " + details);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorJson handleException(final Exception exception) {
        logException(exception);
        return new ErrorJson(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                String.format("A server error encountered: %s", exception.getMessage())
        );
    }

    private void logException(Exception exception) {
        LOG.error("Error during service call.", exception);
    }
}

