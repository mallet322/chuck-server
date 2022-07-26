package ru.elias.server;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.elias.server.dto.CategoryDto;
import ru.elias.server.dto.JokeDto;
import ru.elias.server.model.Category;
import ru.elias.server.model.Joke;

@Component
public class TestDataFactory extends AbstractTestDataFactory {

    private static final String CATEGORY_ENTITIES_REFERENCE = "/data/category-entity-ethalon.json";

    private static final String CATEGORY_ENTITY_REFERENCE = "/data/category-entity.json";

    private static final String CATEGORY_DTO_REFERENCE = "/data/category-dto.json";

    private static final String JOKE_ENTITIES_REFERENCE = "/data/joke-entity-ethalon.json";

    private static final String JOKE_ENTITY_REFERENCE = "/data/joke-entity.json";

    private static final String JOKE_DTO_REFERENCE = "/data/joke-dto.json";

    @SneakyThrows
    public List<Category> categories() {
        return getResource(CATEGORY_ENTITIES_REFERENCE, new TypeReference<>() {
        });
    }

    @SneakyThrows
    public Category category() {
        return getResource(CATEGORY_ENTITY_REFERENCE, new TypeReference<>() {
        });
    }

    @SneakyThrows
    public CategoryDto categoryDto() {
        return getResource(CATEGORY_DTO_REFERENCE, new TypeReference<>() {
        });
    }

    @SneakyThrows
    public List<Joke> jokes() {
        return getResource(JOKE_ENTITIES_REFERENCE, new TypeReference<>() {
        });
    }

    @SneakyThrows
    public Joke joke() {
        return getResource(JOKE_ENTITY_REFERENCE, new TypeReference<>() {
        });
    }

    @SneakyThrows
    public JokeDto jokeDto() {
        return getResource(JOKE_DTO_REFERENCE, new TypeReference<>() {
        });
    }

}
