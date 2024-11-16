package com.aelyashevich.notion.api.controller;

import com.aelyashevich.notion.exception.ExceptionBody;
import com.aelyashevich.notion.exception.ResourceAlreadyExistsException;
import com.aelyashevich.notion.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    private static final String FAILED_VALIDATION_MESSAGE = "Validation failed.";
    private static final String NOT_FOUND_MESSAGE = "Resource was not found.";
    private static final String ALREADY_EXISTS_MESSAGE = "Resource already exists.";

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleNotFound(final ResourceNotFoundException exception) {
        var message = exception.getMessage() == null ? NOT_FOUND_MESSAGE : exception.getMessage();
        log.warn("{} '{}'.", NOT_FOUND_MESSAGE, message);
        return new ExceptionBody(message);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleAlreadyExists(final ResourceAlreadyExistsException exception) {
        var message = exception.getMessage() == null ? ALREADY_EXISTS_MESSAGE : exception.getMessage();
        log.warn("{} '{}'.", ALREADY_EXISTS_MESSAGE, message);
        return new ExceptionBody(message);
    }

    @SuppressWarnings("all")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleValidation(final MethodArgumentNotValidException exception) {
        var errors = exception.getBindingResult()
                .getFieldErrors().stream()
                .collect(Collectors.toMap(
                                FieldError::getField,
                                fieldError -> fieldError.getDefaultMessage(),
                                (exist, newMessage) -> exist + " " + newMessage
                        )
                );
        return new ExceptionBody(FAILED_VALIDATION_MESSAGE, errors);
    }

}
