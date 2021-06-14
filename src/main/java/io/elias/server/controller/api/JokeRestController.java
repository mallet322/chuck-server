package io.elias.server.controller.api;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.elias.server.dto.JokeDTO;
import io.elias.server.mapper.JokeMapper;
import io.elias.server.service.JokeService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/jokes")
public class JokeRestController {

    private final JokeService jokeService;

    private final JokeMapper mapper;

    @GetMapping("/all-jokes")
    public List<JokeDTO> showAllJokes() {
        return jokeService.getAllJokes()
                          .stream()
                          .map(joke -> mapper.toDto(joke))
                          .collect(Collectors.toList());
    }

    @GetMapping("/random-joke")
    public JokeDTO showRandomJoke() {
        return mapper.toDto(jokeService.getRandomJoke());
    }

    @GetMapping("/category/{name}")
    public List<JokeDTO> showJokesByCategory(@PathVariable("name") String name) {
        return mapper.toDto(jokeService.getJokesByCategory(name));
    }

}
