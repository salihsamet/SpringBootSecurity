package com.clinked.articleservice.service;

import com.clinked.articleservice.contoller.ArticleController;
import com.clinked.articleservice.exception.AccessDeniedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {

    public static final Logger LOG = LogManager.getLogger(ArticleController.class);

    @Value("${tokenExpireTimeMS}")
    private  int expireInMs;

    @Value("${tokenAuthoritiesKey}")
    private String AUTHORITIES_KEY;

    private final static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generate(Authentication authentication) {
        String role = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining());
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireInMs))
                .claim(AUTHORITIES_KEY, role)
                .signWith(key)
                .compact();
    }
    public boolean validate(String token){
        try {
            return getUsername(token) != null && isExpired(token);
        }catch (Exception e){
            LOG.error("Access is denied because of that token is invalid.");
            throw new AccessDeniedException("dfd");
        }
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
