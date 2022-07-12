package io.elias.server.exception;

import lombok.Getter;

/**
 * Ошибка, возникающая в бизнес-логике.
 */
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorType errorType;

    /**
     * Создает объект исключения,
     * у которого сообщение об ошибке носит общий, недетализованный характер.
     *
     * @param errorType
     *         Тип ошибки.
     */
    public BusinessException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    /**
     * Создает объект исключения с детализованным сообщением об ошибке.
     *
     * @param errorType
     *         Тип ошибки.
     * @param message
     *         Сообщение об ошибке.
     */
    public BusinessException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

}
