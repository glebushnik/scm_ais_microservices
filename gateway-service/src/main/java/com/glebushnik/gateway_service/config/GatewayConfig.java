package com.glebushnik.gateway_service.config;

import com.glebushnik.gateway_service.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private AuthenticationFilter filter; // Используем инжекцию фильтра

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Маршрут для аутентификации (без валидации токена)
                .route("auth_route", r -> r.path("/api/v1/auth/**")
                        .uri("lb://USER-SERVICE")) // Прокси на USER-SERVICE для аутентификации
                // Все остальные маршруты с валидацией токенов
                .route("secured_routes", r -> r.path("/api/v1/transport/**")
                        .filters(f -> f.filter(filter)) // Применяем фильтр, используя инжектированный фильтр
                        .uri("lb://TRANSPORT-SERVICE")) // Прокси на TRANSPORT-SERVICE
                .build();
    }
}
