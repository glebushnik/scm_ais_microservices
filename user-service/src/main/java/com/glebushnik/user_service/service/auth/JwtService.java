package com.glebushnik.user_service.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final static String SECRET_KEY = "Hvy0sXzEQIZ9AYZIlWPnNSZHgjNzwjG8xSGxWkcjR5xZmZphVK0BMzUTrkx6puM6+WbD3HFa5BQhSn8T1Q2R2SjBB8iw+zxZoc4VgoVb+OghErL7FscTQCLQxq1gUbaVk9oTC3dXZY0HpvWbyF7q9D976Ns14fD56NJyj1O+SvTwSWnOHGesbPS/KEgLf10XziJLrfF0aAlCLn0NMRdXMPFQzykGZO994xzaf4nbGPp5YPb52RQ4drzru41up46l8QS7WvQbkTpf+u4aQPyY0OoSTdouDfzuf7a932fKXZKWkWEDROlb7rCptZNEpM6xePGUda6ETexOxNj49cy2mQ+zOtkte6MN1Y4IPq6LzZc=";
    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String generateToken(String userName) {
        return generateToken(new HashMap<>(), userName);
    }


    public String generateToken(
            Map<String, Object> extraClaims,
            String userName) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*10*120))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
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

    public void validateToken(final String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
    }
}
