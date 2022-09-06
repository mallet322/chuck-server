package ru.elias.server.config;

import java.util.List;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "oauth2",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        authorizationUrl = "http://localhost:8080/oauth2/authorization/google",
                        tokenUrl = "https://www.googleapis.com/oauth2/v4/token"
                )
        )
)
public class OpenApiConfiguration {

//    private static final String TITLE = "Chuck-server REST API Documentation";
//
//    private static final String OAUTH_NAME = "Chuck";
//
//    private static final String SCOPE = "global";
//
//    private static final String SCOPE_DESCRIPTION = "accessEverything";
//
//    private static final String APPLICATION_NAME = "Chuck-server";
//
//    private static final String AUTHORIZATION_URL = "http://localhost:8080/oauth2/authorization/google";
//
//    private static final String TOKEN_URL = "https://www.googleapis.com/oauth2/v4/token";
//
//
//    /**
//     * Описание OpenAPI.
//     */
//    @Bean
//    public OpenAPI restApi() {
//        return new OpenAPI()
//                .paths(new Paths().addPathItem("/", new PathItem()))
//                .info(new Info().title(TITLE))
//                .components(
//                        new Components()
//                                .addSecuritySchemes(OAUTH_NAME, new io.swagger.v3.oas.models.security.SecurityScheme()
//                                        .name(String.format("Application: %s", APPLICATION_NAME))
//                                        .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.OAUTH2)
//                                        .flows(
//                                                new io.swagger.v3.oas.models.security.OAuthFlows()
//                                                        .authorizationCode(
//                                                                new io.swagger.v3.oas.models.security.OAuthFlow()
//                                                                        .authorizationUrl(
//                                                                                AUTHORIZATION_URL
//                                                                        )
//                                                                        .tokenUrl(TOKEN_URL)
//                                                                        .scopes(
//                                                                                new Scopes()
//                                                                                        .addString(
//                                                                                                SCOPE,
//                                                                                                SCOPE_DESCRIPTION
//                                                                                        )
//                                                                        )
//                                                        )
//                                        )
//
//                                )
//                                .addSecuritySchemes(
//                                        "JWT",
//                                        new io.swagger.v3.oas.models.security.SecurityScheme()
//                                                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.APIKEY)
//                                                .description("Bearer Token")
//                                                .in(io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER)
//                                                .name("Authorization")
//                                )
//                )
//                .security(
//                        List.of(
//                                new SecurityRequirement().addList(APPLICATION_NAME),
//                                new SecurityRequirement().addList("JWT")
//                        )
//                );
//    }

}
