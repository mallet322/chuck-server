package io.elias.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.elias.server.model.Joke;
import io.elias.server.service.CategoryService;
import io.elias.server.service.JokeService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/joke")
public class JokeController {

    private final JokeService jokeService;

    private final CategoryService categoryService;

    @GetMapping("/add-joke")
    public String addingJokeForm(Model model) {
        model.addAttribute("newJoke", new Joke());
        model.addAttribute("categoryList", categoryService.findAll());
        return "joke/add-joke";
    }

    @PostMapping("/add-joke")
    public String addedJokeSubmit(@ModelAttribute("newJoke") Joke joke, Model model) {
        jokeService.createJoke(joke);
        model.addAttribute("j", joke);
        return "joke/added-joke";
    }

    @GetMapping("/random-joke")
    public String showRandomJoke(Model model) {
        model.addAttribute("randomJoke", jokeService.getRandomJokeFromChuckAPI());
        return "joke/random-joke";
    }


}
