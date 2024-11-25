package com.glebushnik.gateway_service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final static String SECRET_KEY = "Hvy0sXzEQIZ9AYZIlWPnNSZHgjNzwjG8xSGxWkcjR5xZmZphVK0BMzUTrkx6puM6+WbD3HFa5BQhSn8T1Q2R2SjBB8iw+zxZoc4VgoVb+OghErL7FscTQCLQxq1gUbaVk9oTC3dXZY0HpvWbyF7q9D976Ns14fD56NJyj1O+SvTwSWnOHGesbPS/KEgLf10XziJLrfF0aAlCLn0NMRdXMPFQzykGZO994xzaf4nbGPp5YPb52RQ4drzru41up46l8QS7WvQbkTpf+u4aQPyY0OoSTdouDfzuf7a932fKXZKWkWEDROlb7rCptZNEpM6xePGUda6ETexOxNj49cy2mQ+zOtkte6MN1Y4IPq6LzZc=";
    public <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
