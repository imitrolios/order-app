package org.sharedexpenses.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApiGatewayConfiguration {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("car-service", r -> r.path("/cars")
                        // .filters(f -> f.hystrix(c -> c.setName("carsFallback")
                        // .setFallbackUri("forward:/cars-fallback")))
                        .uri("lb://car-service"))
                .build();
    }

    @Bean
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
}
