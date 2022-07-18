package ru.elias.server.controller.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomErrorAttributes extends DefaultErrorAttributes {

    private static final ErrorAttributeOptions OPTIONS = ErrorAttributeOptions.defaults();

    private final WebRequest webRequest;

    public Map<String, Object> getErrorAttributes(Exception exception, HttpStatus httpStatus) {
        var map = super.getErrorAttributes(webRequest, OPTIONS);
        if (exception instanceof BindException) {
            var e = (BindException) exception;
            var msg = onBindException(e);
            putAttributes(map, httpStatus, exception, msg);
        }
        putAttributes(map, httpStatus, exception, exception.getMessage());
        return map;
    }

    private List<String> onBindException(BindException e) {
        var errorMessages = new ArrayList<String>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorMessages.add(fieldError.getDefaultMessage());
        }
        log.error(String.valueOf(errorMessages), e);
        return errorMessages;
    }

    private void putAttributes(Map<String, Object> map,
                               HttpStatus status,
                               Throwable error,
                               Object msg) {
        map.put("status", status.value());
        map.put("error", status.getReasonPhrase());
        map.put("exception", error.getClass().getCanonicalName());
        map.put("message", msg.toString());
    }

}
