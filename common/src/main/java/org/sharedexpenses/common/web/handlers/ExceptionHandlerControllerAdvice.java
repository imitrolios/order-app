package org.sharedexpenses.common.web.handlers;


import org.sharedexpenses.common.exception.ApplicationBadInputException;
import org.sharedexpenses.common.exception.ApplicationException;
import org.sharedexpenses.common.exception.ApplicationValidationException;
import org.sharedexpenses.common.web.model.ErrorResponseDto;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationBadInputException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody
    ErrorResponseDto handleApplicationValidationException(final ApplicationException e,
                                                          final HttpServletRequest request) {
        return toErrorResponseDto(e, request.getRequestURI(), HttpStatus.BAD_REQUEST.value());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ErrorResponseDto handleException(final Exception e,
                                     final HttpServletRequest request) {

        if (e instanceof ApplicationException) {
            return toErrorResponseDto(
                    new ApplicationValidationException(e.getMessage()),
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        } else {
            return toErrorResponseDto(e, request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    @NonNull
    public ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception e, @Nullable Object body, HttpHeaders headers, HttpStatus status,
            @NonNull WebRequest request) {

        ErrorResponseDto errorResponseDto = toErrorResponseDto(e,
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(errorResponseDto, headers, status);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMissingPathVariable(
            @NonNull MissingPathVariableException ex, @NonNull HttpHeaders headers,
            @NonNull HttpStatus status, @NonNull WebRequest request) {
        return this.handleExceptionInternal(
                new ApplicationBadInputException("Missing path variable in the request"), null, headers,
                status,
                request);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleTypeMismatch(@NonNull TypeMismatchException ex,
                                                        @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        return this
                .handleExceptionInternal(new ApplicationBadInputException("Type mismatch in the request"),
                        null, headers, status,
                        request);
    }

    private ErrorResponseDto toErrorResponseDto(Exception e, String requestUri,
                                                int httpStatus) {

        String message = Optional.ofNullable(e.getMessage()).orElse("No message available");

        return toErrorResponseDto(e, requestUri, httpStatus, message);
    }

    private ErrorResponseDto toErrorResponseDto(Exception e, String requestUri,
                                                int httpStatus, String message) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setError(e.getClass().getSimpleName());
        errorResponseDto.setPath(requestUri);
        errorResponseDto.setMessage(message);
        errorResponseDto.setTimestamp(LocalDateTime.now());
        errorResponseDto.setStatus(httpStatus);

        return errorResponseDto;
    }

}