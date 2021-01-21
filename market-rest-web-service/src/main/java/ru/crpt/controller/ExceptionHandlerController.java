package ru.crpt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.crpt.model.ErrorResponse;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlerController {

    public static final String MESSAGE = "Запрос не прошел валидацию: поля [%s] имеют некорректные значения";

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse errors(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException error: {}", ex.getMessage(), ex);
        final List<String> fieldErrors = getFields(ex);
        if (!fieldErrors.isEmpty()) {
            return new ErrorResponse(
                    String.format(MESSAGE, String.join(", ", fieldErrors))
            );
        }
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse errors(BindException ex) {
        log.error("BindException error: {}", ex.getMessage(), ex);
        return new ErrorResponse(
                String.format(MESSAGE, ex.getBindingResult().getObjectName())
        );
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse errors(ConstraintViolationException ex) {
        log.error("ConstraintViolationException error: {}", ex.getMessage(), ex);
        return new ErrorResponse(
                String.format(MESSAGE, extractErrorValidatedField(ex.getMessage()))
        );
    }

    @ExceptionHandler
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse errors(Exception ex) {
        log.error("Internal error: {}", ex.getMessage(), ex);
        return new ErrorResponse(ex.getMessage());
    }

    private List<String> getFields(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream().map(FieldError::getField).sorted().collect(toList());
    }

    private String extractErrorValidatedField(String errorMessage) {
        String [] errorMessagesParam = errorMessage.split("Param: ");
        return errorMessagesParam[1];
    }
}
