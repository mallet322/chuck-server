package ru.elias.server.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiPathConstants {

    public static final String API_V_1 = "/api/v1";

    public static final String CATEGORIES = "/categories";

    public static final String JOKES = "/jokes";

    public static final String BY_ID = "/{id}";

    public static final String BY_CATEGORY_NAME = "/{categoryName}";

    public static final String RANDOM = "/random";

    public static final String STATISTICS = "/statistics";

    public static final String QUERY = "/query";

}
