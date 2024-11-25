package com.glebushnik.gateway_service.filter;

import com.glebushnik.gateway_service.config.RouterValidator;
import com.glebushnik.gateway_service.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
public class AuthenticationFilter implements GatewayFilter {

    @Autowired
    private RouterValidator routerValidator; // custom route validator
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // Проверка, требует ли маршрут аутентификацию
        if (routerValidator.isSecured.test(request)) {

            // Проверка, присутствует ли заголовок Authorization
            if (this.isAuthMissing(request)) {
                return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
            }

            final String token = this.getAuthHeader(request);

            // Проверка на валидность токена
            if (jwtUtil.isInvalid(token)) {
                return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
            }

            // Добавление информации о пользователе в запрос
            this.populateRequestWithHeaders(exchange, token);
        }

        return chain.filter(exchange);
    }

    /*PRIVATE*/

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        // Извлечение токена из заголовка Authorization
        String authHeader = request.getHeaders().getOrEmpty("Authorization").get(0);

        // Проверка, начинается ли токен с "Bearer "
        if (authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Убираем "Bearer " из начала строки
        } else {
            throw new IllegalArgumentException("Authorization header must start with Bearer ");
        }
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        // Проверка, есть ли заголовок Authorization
        return !request.getHeaders().containsKey("Authorization");
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        // Извлечение данных из токена
        Claims claims = jwtUtil.extractAllClaims(token);

        // Добавление данных из токена в заголовки запроса
        exchange.getRequest().mutate()
                .header("id", String.valueOf(claims.get("id")))
                .header("role", String.valueOf(claims.get("role")))
                .build();
    }
}
