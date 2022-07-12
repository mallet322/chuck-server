package io.elias.server.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(ApiPathConstants.RANDOM)
    public ResponseEntity<JokeDto> getRandomeJoke() {
        return jokeService.getRandomJoke();
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestParam(value = "auto") boolean flag,
                                       @RequestParam(value = "category",
                                                     required = false) String category,
                                       @RequestBody(required = false) JokeDto request) {
        return jokeService.createJoke(flag, category, request);
    }

}
