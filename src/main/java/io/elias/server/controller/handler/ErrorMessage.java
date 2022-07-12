package io.elias.server.controller.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Сообщение об ощибке.
 */
@Data
@AllArgsConstructor
public class ErrorMessage {

    private String code;

    private String message;

}
