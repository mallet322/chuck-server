package ru.elias.server;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.elias.server.dto.JokeDto;
import ru.elias.server.model.Joke;

@Component

public class JokeDataFactory extends AbstractTestDataFactory {

    private static final String JOKE_ENTITIES_REFERENCE = "/data/joke-entity-ethalon.json";

    private static final String JOKE_ENTITY_REFERENCE = "/data/joke-entity.json";

    private static final String JOKE_DTO_REFERENCE = "/data/joke-dto.json";

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
