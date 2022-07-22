package ru.elias.server.service.impl.unit;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody().getName()).isEqualTo(expected.getName());
        Mockito.verify(categoryMapper).map(ArgumentMatchers.any(Category.class));
        Mockito.verifyNoMoreInteractions(categoryMapper, jokeClient, messageSourceHelper);
    }

    @Test
    void whenGetCategoryByNameThenThrowException() {
        Mockito.when(categoryRepository.findCategoryByName(ArgumentMatchers.anyString()))
               .thenReturn(Optional.empty());
        Mockito.when(messageSourceHelper.getMessage(ArgumentMatchers.any(ErrorType.class), ArgumentMatchers.any()))
               .thenReturn("some-msg");
        assertThatThrownBy(() -> categoryService.getCategoryByName("some-cat"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("some-msg");
        Mockito.verify(messageSourceHelper).getMessage(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(categoryMapper, jokeClient, messageSourceHelper);
    }

    @Test
    void whenGetAllCategoriesThenReturnListDtoCategories() {
        var mockedCategories = List.of(
                Category.builder().name("some-cat").build(),
                Category.builder().name("some-cat").build(),
                Category.builder().name("some-cat").build()
        );

        var mockedDtos = List.of(
                CategoryDto.builder().name("some-cat").build(),
                CategoryDto.builder().name("some-cat").build(),
                CategoryDto.builder().name("some-cat").build()
        );
        Mockito.when(categoryRepository.findAll())
               .thenReturn(mockedCategories);
        Mockito.when(categoryMapper.map(ArgumentMatchers.any(Category.class)))
                .thenReturn(mockedDtos.get(0));
        var actual = categoryService.getAllCategories();
        assertThat(actual.getBody())
                .isNotNull()
                .hasSize(mockedDtos.size());
        var expectedCategoryNames = mockedCategories.stream()
                                                    .map(Category::getName)
                                                    .toArray(String[]::new);
        assertThat(actual.getBody())
                .flatExtracting(CategoryDto::getName)
                .containsExactlyInAnyOrder(expectedCategoryNames);
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
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Mockito.verify(categoryMapper, Mockito.times(1)).map(ArgumentMatchers.any(CategoryDto.class));
        Mockito.verify(categoryRepository, Mockito.times(1)).save(ArgumentMatchers.any(Category.class));
        Mockito.verifyNoMoreInteractions(jokeClient);
    }

    @Test
    void whenCreateCategoryOnAutoMode() {
        var categoryNames = List.of("some-cat-1", "some-cat-2", "some-cat-3");
        Mockito.when(jokeClient.getAllCategories()).thenReturn(categoryNames);
        var actual = categoryService.createCategories(true, null);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Mockito.verify(jokeClient, Mockito.times(1)).getAllCategories();
        Mockito.verify(categoryMapper, Mockito.times(3)).map(ArgumentMatchers.anyString());
        Mockito.verify(categoryRepository, Mockito.times(3)).findAll();
        Mockito.verify(categoryRepository, Mockito.times(1)).saveAll(ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(categoryMapper, jokeClient, messageSourceHelper);
    }

}