package ru.elias.server.util;

import lombok.experimental.UtilityClass;
import ru.elias.server.model.QCategory;
import ru.elias.server.model.QJoke;

@UtilityClass
public class QEntities {

    public static final QCategory CATEGORY = QCategory.category;

    public static final QJoke JOKE = QJoke.joke;

}
