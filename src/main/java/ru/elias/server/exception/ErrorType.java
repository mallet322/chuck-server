package ru.elias.server.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    INTERNAL_SERVER_ERROR("E001", "Internal server error"),
    BAD_REQUEST("400", "Bad request"),

    CATEGORY_NOT_FOUND_BY_NAME("C001", "Category not found by name!"),

    JOKE_NOT_FOUND_BY_ID("J001", "Joke not found by id!"),

    JOKE_NOT_FOUND_FROM_INTEGRATION("J002", "Joke not found from integration"),

    USER_NOT_FOUND_BY_ID("U001", "User not found by id"),

    USER_NOT_FOUND_BY_NAME("U002", "User not found by name");

    private final String code;

    private final String message;

}
