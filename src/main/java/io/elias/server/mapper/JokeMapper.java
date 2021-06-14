package io.elias.server.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import io.elias.server.dto.JokeDTO;
import io.elias.server.model.Joke;

@Component
public class JokeMapper {

    public JokeDTO toDto(Joke joke) {
        return JokeDTO.builder()
                      .joke(joke.getValue())
                      .category(joke.getCategory().getName())
                      .build();
    }

    public List<JokeDTO> toDto(List<Joke> jokes) {
        return jokes.stream()
                    .map(joke -> JokeDTO.builder()
                                        .joke(joke.getValue())
                                        .category(joke.getCategory().getName())
                                        .build())
                    .collect(Collectors.toList());
    }

}
