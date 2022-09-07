package ru.elias.server.controller.api;

import javax.validation.Valid;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.server.dto.CategoryDto;
import ru.elias.server.service.CategoryService;
import ru.elias.server.util.ApiPathConstants;

@Tag(name = "Categories")
@RestController
@RequestMapping(ApiPathConstants.API_V_1 + ApiPathConstants.CATEGORIES)
@RequiredArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    @Operation(summary = "Get category by name")
    @GetMapping("{categoryName}")
    public ResponseEntity<CategoryDto> getCategoryByName(
            @Parameter(description = "Category name")
            @PathVariable("categoryName") String name) {
        return categoryService.getCategoryByName(name);
    }

    @Operation(summary = "Get all categories")
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @Operation(summary = "Create category")
    @PostMapping
    public ResponseEntity<Void> create(
            @Parameter(description = "Creating mode (auto/manual)")
            @RequestParam(value = "auto") boolean flag,
            @Valid
            @RequestBody(required = false) CategoryDto request) {
        return categoryService.createCategories(flag, request);
    }

}
