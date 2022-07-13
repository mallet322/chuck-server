package io.elias.server.controller.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.elias.server.exception.BusinessException;
import io.elias.server.exception.ErrorType;

/**
 * Spring AOP Advice обработки ошибок контроллера.
 */
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ErrorHandlerControllerAdvice {

    /**
     * Обработчик ошибок {@link BusinessException}.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorMessage> handleNotFroundException(BusinessException e) {
        var errorType = e.getErrorType();
        return switch (errorType) {
            case CATEGORY_NOT_FOUND_BY_NAME, JOKE_NOT_FOUND_BY_ID ->
                    getErrorResponse(HttpStatus.NOT_FOUND,
                                     new ErrorMessage(errorType.getCode(),
                                                      e.getMessage()));
            default -> getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                                        new ErrorMessage(ErrorType.INTERNAL_SERVER_ERROR.getCode(),
                                                         e.getMessage()));
        };
    }

    private ResponseEntity<ErrorMessage> getErrorResponse(HttpStatus status,
                                                          ErrorMessage errorMessage) {
        return ResponseEntity.status(status)
                             .body(errorMessage);
    }

}
