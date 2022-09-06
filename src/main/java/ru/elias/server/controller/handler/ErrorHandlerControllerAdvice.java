package ru.elias.server.controller.handler;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.elias.server.exception.BusinessException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ErrorHandlerControllerAdvice {

    private final CustomErrorAttributes customErrorAttributes;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var body = customErrorAttributes.getErrorAttributes(e, status);
        return ResponseEntity.status(status)
                             .body(body);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(BusinessException e) {
        var errorType = e.getErrorType();
        switch (errorType) {
            case CATEGORY_NOT_FOUND_BY_NAME:
            case JOKE_NOT_FOUND_BY_ID:
            case JOKE_NOT_FOUND_FROM_INTEGRATION:
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body(customErrorAttributes.getErrorAttributes(e, HttpStatus.NOT_FOUND));
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                     .body(customErrorAttributes.getErrorAttributes(
                                             e,
                                             HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, Object>> handleBindException(BindException e) {
        var status = HttpStatus.BAD_REQUEST;
        var body = customErrorAttributes.getErrorAttributes(e, status);
        return ResponseEntity.status(status)
                             .body(body);
    }

}
