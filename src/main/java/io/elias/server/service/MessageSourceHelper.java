package io.elias.server.service;

import java.util.Locale;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import io.elias.server.exception.ErrorType;

@Service
@RequiredArgsConstructor
public class MessageSourceHelper {

    private final MessageSource messageSource;

    /**
     * Отдает сообщение об ошибке, соответствующее указанному типу, с указанными аргументами.
     *
     * @param errorType
     *         Тип ошибки.
     * @param placeholders
     *         Аргументы.
     *
     * @return Сообщение.
     */
    public String getMessage(ErrorType errorType, Object... placeholders) {
        return getMessage(Locale.getDefault(), errorType, placeholders);
    }

    /**
     * Отдает сообщение об ошибке, соответствующее указанному типу, с указанными аргументами.
     * Язык сообщения определяется указанной локалью.
     *
     * @param locale
     *         Локаль для определения языка сообщения.
     * @param errorType
     *         Тип ошибки.
     * @param placeholders
     *         Аргументы.
     *
     * @return Сообщение.
     */
    public String getMessage(Locale locale, ErrorType errorType, Object... placeholders) {
        return messageSource.getMessage(
                errorType.name().toLowerCase(),
                placeholders,
                locale
        );
    }
}
