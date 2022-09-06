package ru.elias.server.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OpenApiConstants {

    public static final String TITLE = "Chuck-server REST API Documentation";

    public static final String OAUTH2 = "OAuth2";

    public static final String OAUTH2_AUTHORIZATION_URL = "http://localhost:8080/oauth2/authorization/google";

    public static final String OAUTH2_TOKEN_URL = "https://www.googleapis.com/oauth2/v4/token";

    public static final String OAUTH2_DESCRIPTION = "Authorization with Google";

    public static final String OAUTH2_GLOBAL_SCOPE = "global";

    public static final String OAUTH2_ACCESS_SCOPE = "accessEverything";

    public static final String JWT = "JWT";

    public static final String JWT_DESCRIPTION = "Bearer Token";

}
