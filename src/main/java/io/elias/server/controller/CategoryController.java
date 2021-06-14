package io.elias.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.elias.server.service.CategoryService;
import io.elias.server.service.JokeService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    private final JokeService jokeService;

    @GetMapping()
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/allCategories";
    }

    @GetMapping("/{name}")
    public String showJokeFromCategory(@PathVariable("name") String name, Model model) {
        model.addAttribute("jokeFromCategory", jokeService.getJokeByCategoryFromChuckAPI(name));
        return "joke/jokeFromCategory";
    }


}
