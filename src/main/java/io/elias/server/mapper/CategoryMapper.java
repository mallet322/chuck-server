package io.elias.server.mapper;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import io.elias.server.dto.CategoryDTO;
import io.elias.server.model.Category;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final JokeMapper jokeMapper;

    public CategoryDTO toDto(Category category) {
        return CategoryDTO.builder()
                          .name(category.getName())
                          .jokes(category.getJokes()
                                         .stream()
                                         .map(joke -> jokeMapper.toDto(joke))
                                         .collect(Collectors.toList()))
                          .build();
    }

    public List<CategoryDTO> toDto(List<Category> categories) {
        return categories.stream()
                         .map(category -> CategoryDTO.builder()
                                                     .name(category.getName())
                                                     .jokes(category.getJokes()
                                                                    .stream()
                                                                    .map((joke -> jokeMapper.toDto(joke)))
                                                                    .collect(Collectors.toList()))
                                                     .build())
                         .collect(Collectors.toList());
    }

}
