package org.orderapi.gateway.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@PreAuthorize("hasRole('ADMIN')")
public class UserService {

    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:8086/user").
            defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

    @PreAuthorize("isAnonymous() or isAuthenticated()")
    public Mono<User> findOneByEmail(String email) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                .path("/search/email/{email}")
                .build(email)).retrieve().bodyToMono(User.class);
    }

}