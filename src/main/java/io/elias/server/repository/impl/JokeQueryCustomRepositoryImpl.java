package io.elias.server.repository.impl;

import javax.persistence.EntityManager;

import java.util.List;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import io.elias.server.model.Joke;
import io.elias.server.repository.JokeQueryCustomRepository;
import io.elias.server.util.QEntities;

@Repository
@RequiredArgsConstructor
public class JokeQueryCustomRepositoryImpl implements JokeQueryCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<Joke> findJokesByPredicate(Predicate predicate) {
        return new JPAQuery<>(entityManager)
                .select(QEntities.JOKE)
                .from(QEntities.JOKE)
                .join(QEntities.JOKE.category, QEntities.CATEGORY)
                .where(predicate)
                .fetch();
    }

}
