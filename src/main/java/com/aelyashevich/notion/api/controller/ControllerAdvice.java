package com.aelyashevich.notion.api.controller;

import com.aelyashevich.notion.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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
    private static final String TOKEN_VALIDATION_MESSAGE = "Token is not valid.";
    private static final String NOT_SUPPORTED_MESSAGE = "Http method with this URL not found.";
    private static final String TOKEN_EXPIRED_MESSAGE = "JWT expired.";
    private static final String ACCESS_DENIED_MESSAGE = "Access denied.";
    private static final String UNEXPECTED_ERROR_MESSAGE = "Something went wrong.";
    private static final String INVALID_TOKEN_SIGNATURE_MESSAGE = "JWT signature does not match locally computed signature.";
    private static final String INVALID_PASSWORD_MESSAGE = "Password mismatch.";


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleNotFound(final ResourceNotFoundException exception) {
        return this.handleException(exception, NOT_FOUND_MESSAGE);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleAlreadyExists(final ResourceAlreadyExistsException exception) {
        return this.handleException(exception, ALREADY_EXISTS_MESSAGE);
    }

    @ExceptionHandler(InvalidJWTTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionBody handleInvalidJWTToken(final InvalidJWTTokenException exception) {
        return this.handleException(exception, TOKEN_VALIDATION_MESSAGE);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleException(final HttpRequestMethodNotSupportedException exception) {
        return this.handleException(exception, NOT_SUPPORTED_MESSAGE);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleInvalidPassword(final InvalidPasswordException exception) {
        return this.handleException(exception, INVALID_PASSWORD_MESSAGE);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionBody handleTokenExpire(final ExpiredJwtException exception) {
        return this.handleException(exception, TOKEN_EXPIRED_MESSAGE);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleDeniedException(final AuthorizationDeniedException exception) {
        return this.handleException(exception, ACCESS_DENIED_MESSAGE);
    }


    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionBody handleSignature(final SignatureException exception) {
        return this.handleException(exception, INVALID_TOKEN_SIGNATURE_MESSAGE);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleException(final RuntimeException exception) {
        exception.printStackTrace();
        log.error(exception.getMessage(), exception.getCause());
        return new ExceptionBody(UNEXPECTED_ERROR_MESSAGE);
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

    private ExceptionBody handleException(final Exception exception, final String defaultMessage) {
        var message = exception.getMessage() == null ? defaultMessage : exception.getMessage();
        log.warn("{} '{}'.", defaultMessage, message);
        return new ExceptionBody(message);
    }

}
