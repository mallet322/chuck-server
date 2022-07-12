package io.elias.server.controller.api;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.elias.server.dto.CategoryDto;
import io.elias.server.service.CategoryService;
import io.elias.server.util.ApiPathConstants;

@RestController
@RequestMapping(ApiPathConstants.API_V_1 + ApiPathConstants.CATEGORIES)
@RequiredArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping("{categoryName}")
    public ResponseEntity<CategoryDto> getCategoryByName(@PathVariable("categoryName")
                                                         String name) {
        return categoryService.getCategoryByName(name);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestParam(value = "auto") boolean flag,
                                       @RequestBody(required = false) CategoryDto request) {
        return categoryService.createCategories(flag, request);
    }

}
