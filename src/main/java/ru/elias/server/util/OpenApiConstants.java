package ru.elias.server.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OpenApiConstants {

    public static final String TITLE = "Chuck-server REST API Documentation";

    public static final String OAUTH2 = "OAuth2";

    public static final String OAUTH2_AUTHORIZATION_URL = "https://accounts.google.com/o/oauth2/auth";

    public static final String OAUTH2_TOKEN_URL = "https://www.googleapis.com/oauth2/v4/token";

    public static final String OAUTH2_DESCRIPTION = "Authorization with Google";

    public static final String OAUTH2_OPENID_SCOPE = "openid";

    public static final String OAUTH2_EMAIL_SCOPE = "email";

    public static final String OAUTH2_PROFILE_SCOPE = "profile";

    public static final String JWT = "JWT";

    public static final String JWT_DESCRIPTION = "Bearer Token";

}
