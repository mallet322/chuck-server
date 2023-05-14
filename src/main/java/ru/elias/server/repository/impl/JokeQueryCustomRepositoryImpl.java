package ru.elias.server.repository.impl;

import javax.persistence.EntityManager;

import java.util.List;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import ru.elias.server.dto.JokesGeneralStatistic;
import ru.elias.server.model.Joke;
import ru.elias.server.model.QCategory;
import ru.elias.server.model.QJoke;
import ru.elias.server.repository.JokeQueryCustomRepository;
import ru.elias.server.util.QEntities;

@Repository
public class JokeQueryCustomRepositoryImpl implements JokeQueryCustomRepository {

    private static final QJoke JOKES = QEntities.JOKE;

    private static final QCategory CATEGORIES = QEntities.CATEGORY;

    private final JPAQueryFactory queryFactory;

    protected JokeQueryCustomRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Joke> findJokesByPredicate(Predicate predicate) {
        return queryFactory
                .select(QEntities.JOKE)
                .from(QEntities.JOKE)
                .join(QEntities.JOKE.category, QEntities.CATEGORY)
                .where(predicate)
                .fetch();
    }

    @Override
    public List<JokesGeneralStatistic> countByCategories() {
        return queryFactory
                .from(CATEGORIES)
                .join(CATEGORIES.jokes, JOKES)
                .select(Projections.constructor(
                        JokesGeneralStatistic.class,
                        CATEGORIES.name,
                        JOKES.category.id.count()))
                .groupBy(CATEGORIES.name)
                .fetch();
    }

}
