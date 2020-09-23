package org.orderapi.gateway.configuration.security;

import org.orderapi.gateway.security.LibraryReactiveUserDetailsService;
import org.orderapi.gateway.security.LibraryUserJwtAuthenticationConverter;
import org.orderapi.gateway.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfiguration {
    private final LibraryReactiveUserDetailsService libraryReactiveUserDetailsService;

    @Autowired
    public WebSecurityConfiguration(
            LibraryReactiveUserDetailsService libraryReactiveUserDetailsService) {
        this.libraryReactiveUserDetailsService = libraryReactiveUserDetailsService;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf()
                .disable()
                .authorizeExchange()
                .matchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll()
                .matchers(EndpointRequest.to("health"))
                .permitAll()
                .matchers(EndpointRequest.to("info"))
                .permitAll()
                .matchers(EndpointRequest.toAnyEndpoint())
                .hasRole(Role.FRONT_OFFICE.name())
                .pathMatchers(HttpMethod.POST, "/orders")
                .hasRole(Role.FRONT_OFFICE.name())
                .anyExchange()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(libraryUserJwtAuthenticationConverter());
        return http.build();
    }

    @Bean
    public LibraryUserJwtAuthenticationConverter libraryUserJwtAuthenticationConverter() {
        return new LibraryUserJwtAuthenticationConverter(libraryReactiveUserDetailsService);
    }


    @Bean
    CorsConfigurationSource corsConfiguration() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.applyPermitDefaultValues();
        corsConfig.addAllowedMethod(HttpMethod.PUT);
        corsConfig.addAllowedMethod(HttpMethod.DELETE);
        corsConfig.addAllowedMethod(HttpMethod.OPTIONS);
        corsConfig.addAllowedMethod(HttpMethod.GET);
        corsConfig.addAllowedMethod(HttpMethod.POST);
        corsConfig.setAllowedOrigins(List.of("http://localhost:4200"));

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
}
