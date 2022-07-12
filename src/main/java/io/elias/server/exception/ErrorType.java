package io.elias.server.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    INTERNAL_SERVER_ERROR("E001", "Internal server error"),
    BAD_REQUEST("400", "Bad request"),

    CATEGORY_NOT_FOUND_BY_NAME("C001", "Category not found by name!");

    private final String code;

    private final String message;

}
