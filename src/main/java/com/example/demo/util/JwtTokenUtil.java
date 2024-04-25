package com.example.demo.util;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

  private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  @Value("${jwt.expiration}")
  private Duration jwtExpirationInM;

  public String generateToken(String username) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("sub", username);
    return doGenerateToken(claims);
  }

  private String doGenerateToken(Map<String, Object> claims) {
    return Jwts.builder()
        .setClaims(claims)
        .setIssuer("ExampleIssuer")
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInM.toMillis()))
        .signWith(secretKey)
        .compact();
  }

  public Boolean validateToken(String token, String username) {
    final String usernameFromToken = getUsernameFromToken(token);
    return (username.equals(usernameFromToken) && !isTokenExpired(token));
  }

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  private Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody();
    return claimsResolver.apply(claims);
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }
}
