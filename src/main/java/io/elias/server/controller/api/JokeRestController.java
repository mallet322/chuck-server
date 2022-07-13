package io.elias.server.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.elias.server.dto.JokeDto;
import io.elias.server.service.JokeService;
import io.elias.server.util.ApiPathConstants;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstants.API_V_1 + ApiPathConstants.JOKES)
public class JokeRestController {

    private final JokeService jokeService;

    @GetMapping(ApiPathConstants.BY_ID)
    public ResponseEntity<JokeDto> getJokeById(@PathVariable("id") Long id) {
        return jokeService.getJokeById(id);
    }

    @GetMapping(ApiPathConstants.RANDOM)
    public ResponseEntity<JokeDto> getRandomJoke() {
        return jokeService.getRandomJoke();
    }

    @GetMapping(ApiPathConstants.RANDOM + ApiPathConstants.BY_CATEGORY_NAME)
    public ResponseEntity<JokeDto> getRandomJokeByCategory(@PathVariable("categoryName") String categoryName) {
        return jokeService.getRandomJokeByCategory(categoryName);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestParam(value = "auto") boolean flag,
                                       @RequestParam(value = "category",
                                                     required = false) String category,
                                       @RequestBody(required = false) JokeDto request) {
        return jokeService.createJoke(flag, category, request);
    }

}
