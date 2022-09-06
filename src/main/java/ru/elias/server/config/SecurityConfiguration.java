package ru.elias.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST =
            new String[] {
                    "/login",
                    "/registration",
                    "/v3/api-doc/**",
                    "/swagger-ui/**"
            };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .antMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(loginConfig -> loginConfig
                        .loginPage(AUTH_WHITELIST[0]))
                .oauth2Login(oauth2Config -> oauth2Config
                        .loginPage(AUTH_WHITELIST[0]));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
