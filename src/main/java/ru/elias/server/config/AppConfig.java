package ru.elias.server.config;

import javax.net.ssl.TrustManagerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import io.netty.handler.ssl.SslContextBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ResourceUtils;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import ru.elias.server.config.properties.WebClientProperties;

@Slf4j
@Configuration
//@EnableConfigurationProperties(WebClientProperties.class)
//@RequiredArgsConstructor
public class AppConfig {

//    public static final String TRUST_MANAGER_ALGORITHM = "SunX509";
//
//    private final WebClientProperties webClientProperties;
//
//    @Bean
//    public WebClient upAgpWebClient() throws IOException, GeneralSecurityException {
//        var sslContextBuilder = SslContextBuilder.forClient();
//
//        var trustStorePassword = webClientProperties.getTrustStorePassword().toCharArray();
//        var trustStoreFile = ResourceUtils.getFile(webClientProperties.getTrustStoreLocation());
//        var trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//        trustStore.load(new FileInputStream(trustStoreFile), trustStorePassword);
//
//        var trustManagerFactory = TrustManagerFactory.getInstance(TRUST_MANAGER_ALGORITHM);
//        trustManagerFactory.init(trustStore);
//        sslContextBuilder.trustManager(trustManagerFactory);
//
//        var sslContext = sslContextBuilder.build();
//        var httpClient = HttpClient.create()
//                                   .secure(t -> t.sslContext(sslContext));
//        return WebClient.builder()
//                        .clientConnector(new ReactorClientHttpConnector(httpClient))
//                        .baseUrl(webClientProperties.getUrl())
//                        .filter(logResponse())
//                        .build();
//    }
//
//    private static ExchangeFilterFunction logResponse() {
//        return ExchangeFilterFunction.ofResponseProcessor(response -> {
//            log.info("UP AGP answered with {}", response.statusCode());
//            return Mono.just(response);
//        });
//    }

    @Bean
    public WebClient jokeWebClient(@Value("${webclient.url}") String url) {
        return WebClient.builder()
                        .baseUrl(url)
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
