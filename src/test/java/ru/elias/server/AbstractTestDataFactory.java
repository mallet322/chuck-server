package ru.elias.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractTestDataFactory {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Генерирует тестовые данные их ресурсов.
     *
     * @param path
     *         Путь до ресурса.
     * @param typeReference
     *         Ссылка на тип.
     * @param <T>
     *         Тип.
     *
     * @return Объект с тестовыми данными.
     */
    @SneakyThrows
    public <T> T getResource(String path, TypeReference<T> typeReference) {
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(getClass().getResource(path), typeReference);
    }

}
