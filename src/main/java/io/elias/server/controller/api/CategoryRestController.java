package io.elias.server.controller.api;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.elias.server.dto.CategoryDTO;
import io.elias.server.mapper.CategoryMapper;
import io.elias.server.service.CategoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/categories")
public class CategoryRestController {

    private final CategoryService categoryService;

    private final CategoryMapper mapper;

    @GetMapping("/all-categories")
    public List<CategoryDTO> showAllCategories() {
        var categories = categoryService.findAll();
        return mapper.toDto(categories);
    }

    @GetMapping("/{name}")
    public CategoryDTO showCategoryByName(@PathVariable("name") String name) {
        var category = categoryService.getCategoryByName(name);
        return mapper.toDto(category);
    }

}
