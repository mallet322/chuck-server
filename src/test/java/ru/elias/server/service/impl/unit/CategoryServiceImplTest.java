package ru.elias.server.service.impl.unit;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import ru.elias.server.client.JokeClient;
import ru.elias.server.dto.CategoryDto;
import ru.elias.server.exception.BusinessException;
import ru.elias.server.exception.ErrorType;
import ru.elias.server.mapper.CategoryMapper;
import ru.elias.server.model.Category;
import ru.elias.server.repository.CategoryRepository;
import ru.elias.server.service.MessageSourceHelper;
import ru.elias.server.service.impl.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private JokeClient jokeClient;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private MessageSourceHelper messageSourceHelper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void whenGetCategoryByNameThenReturnCategoryDto() {
        var mockedCategory = Category.builder().name("some-cat").build();
        var mockedDto = CategoryDto.builder().name("some-cat").build();
        Mockito.when(categoryRepository.findCategoryByName(ArgumentMatchers.anyString()))
               .thenReturn(Optional.of(mockedCategory));
        Mockito.when(categoryMapper.map(ArgumentMatchers.any(Category.class)))
               .thenReturn(mockedDto);
        var expected = CategoryDto.builder().name("some-cat").build();
        var actual = categoryService.getCategoryByName("some-cat");
        Assertions.assertNotNull(actual.getBody());
        Assertions.assertEquals(expected.getName(), actual.getBody().getName());
        Mockito.verify(categoryMapper).map(ArgumentMatchers.any(Category.class));
        Mockito.verifyNoMoreInteractions(categoryMapper, jokeClient, messageSourceHelper);
    }

    @Test
    void whenGetCategoryByNameThenThrowException() {
        Mockito.when(categoryRepository.findCategoryByName(ArgumentMatchers.anyString()))
               .thenReturn(Optional.empty());
        Mockito.when(messageSourceHelper.getMessage(ArgumentMatchers.any(ErrorType.class), ArgumentMatchers.any()))
               .thenReturn("some-msg");
        Assertions.assertThrows(BusinessException.class,
                                () -> categoryService.getCategoryByName("some-cat"));
        Mockito.verify(messageSourceHelper).getMessage(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(categoryMapper, jokeClient, messageSourceHelper);
    }

    @Test
    void whenGetAllCategoriesThenReturnListDtoCategories() {
        var mockedCategories = List.of(
                Category.builder().name("some-cat-1").build(),
                Category.builder().name("some-cat-2").build(),
                Category.builder().name("some-cat-3").build()
        );

        var mockedDtos = List.of(
                CategoryDto.builder().name("some-cat-1").build(),
                CategoryDto.builder().name("some-cat-2").build(),
                CategoryDto.builder().name("some-cat-3").build()
        );
        Mockito.when(categoryRepository.findAll())
               .thenReturn(mockedCategories);
        var actual = categoryService.getAllCategories();
        Assertions.assertNotNull(actual.getBody());
        Assertions.assertEquals(mockedDtos.size(), actual.getBody().size());
        Mockito.verify(categoryMapper, Mockito.times(3)).map(ArgumentMatchers.any(Category.class));
        Mockito.verifyNoMoreInteractions(categoryMapper, jokeClient, messageSourceHelper);
    }

    @Test
    void whenCreateCategoryOnManualMode() {
        var mockedCategory = Category.builder().name("some-cat").build();
        var mockedDto = CategoryDto.builder().name("some-cat").build();
        Mockito.when(categoryRepository.save(ArgumentMatchers.any(Category.class)))
                .thenReturn(mockedCategory);
        Mockito.when(categoryMapper.map(ArgumentMatchers.any(CategoryDto.class)))
                .thenReturn(mockedCategory);
        var actual = categoryService.createCategories(false, mockedDto);
        Assertions.assertEquals(HttpStatus.CREATED.value(), actual.getStatusCode().value());
        Mockito.verify(categoryMapper, Mockito.times(1)).map(ArgumentMatchers.any(CategoryDto.class));
        Mockito.verify(categoryRepository, Mockito.times(1)).save(ArgumentMatchers.any(Category.class));
        Mockito.verifyNoMoreInteractions(jokeClient);
    }

    @Test
    void whenCreateCategoryOnAutoMode() {
        var categoryNames = List.of("some-cat-1", "some-cat-2", "some-cat-3");
        Mockito.when(jokeClient.getAllCategories()).thenReturn(categoryNames);
        var actual = categoryService.createCategories(true, null);
        Assertions.assertEquals(HttpStatus.CREATED.value(), actual.getStatusCode().value());
        Mockito.verify(jokeClient, Mockito.times(1)).getAllCategories();
        Mockito.verify(categoryMapper, Mockito.times(3)).map(ArgumentMatchers.anyString());
        Mockito.verify(categoryRepository, Mockito.times(3)).findAll();
        Mockito.verify(categoryRepository, Mockito.times(1)).saveAll(ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(categoryMapper, jokeClient, messageSourceHelper);
    }

}