package com.glebushnik.gateway_service.config;

import com.glebushnik.gateway_service.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
public class GatewayConfig {

    @Autowired
    private AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth_route", r -> r.path("/api/v1/auth/**")
                        .uri("lb://USER-SERVICE"))
                .route("secured_routes", r -> r.path("/api/v1/transport/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://TRANSPORT-SERVICE"))
                .route("secured_routes", r -> r.path("/api/v1/warehouse/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://WAREHOUSE-SERVICE"))
                .route("secured_routes", r -> r.path("/api/v1/company/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://COMPANY-SERVICE"))
                .build();
    }

    @Bean
    CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("*"));
        corsConfig.setMaxAge(8000L);
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }

}