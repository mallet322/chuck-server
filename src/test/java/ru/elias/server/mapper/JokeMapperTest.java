package ru.elias.server.mapper;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.elias.server.AbstractSpringTest;
import ru.elias.server.model.Category;
import ru.elias.server.repository.CategoryRepository;

class JokeMapperTest extends AbstractSpringTest {

    @Autowired
    private JokeMapper mapper;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    void whenMapDtoToJoke() {
        var dto = dataFactory.jokeDto();
        var categoryStub = Category.builder()
                                   .name(dto.getCategory())
                                   .build();
        when(categoryRepository.findCategoryByName(anyString())).thenReturn(Optional.of(categoryStub));
        var actual = mapper.map(dto);
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(dto.getJoke());
        assertThat(actual.getCategory()).isNotNull();
        assertThat(actual.getCategory().getName()).isEqualTo(categoryStub.getName());
    }

    @Test
    void whenMapJokeToDto() {
        var entity = dataFactory.joke();
        var actual = mapper.map(entity);
        assertThat(actual).isNotNull();
        assertThat(actual.getJoke()).isEqualTo(entity.getName());
    }

}