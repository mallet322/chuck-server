package io.elias.server.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constant {

    public static final String CATEGORIES =
            "https://api.chucknorris.io/jokes/categories";
    public static final String JOKE_FROM_CATEGORY =
            "https://api.chucknorris.io/jokes/random?category={category}";
    public static final String RANDOM_JOKE =
            "https://api.chucknorris.io/jokes/random";

}
