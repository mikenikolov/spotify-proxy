package com.example.spotifyproxy.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2Service;
    @Value("${spotify.security.registration.id}")
    private String registrationId;
    @Value("${spotify.security.client.id}")
    private String clientId;
    @Value("${spotify.security.client.secret}")
    private String clientSecret;
    @Value("${spotify.security.token.uri}")
    private String tokenUri;
    @Value("${spotify.security.authorization.uri}")
    private String authUri;
    @Value("${spotify.security.redirect.uri}")
    private String redirectUrl;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .anyRequest()
                    .permitAll()
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                        .userService(oAuth2Service)
                .and()
                    .defaultSuccessUrl("/v1/search", true)
                .and()
                .headers().frameOptions().disable();
        return http.build();
    }

    @Bean
    public ClientRegistration clientRegistration() {
        return ClientRegistration
                .withRegistrationId(registrationId)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AUTHORIZATION_CODE)
                .tokenUri(tokenUri)
                .authorizationUri(authUri)
                .redirectUri(redirectUrl)
                .build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(List<ClientRegistration> registrations) {
        return new InMemoryClientRegistrationRepository(registrations);
    }
}
