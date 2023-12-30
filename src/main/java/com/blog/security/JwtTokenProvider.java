package com.blog.security;

import com.blog.exception.BlogApiException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationMs;

    //generate token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        //expire date

        Date expireDate = new Date(currentDate.getTime()+jwtExpirationMs);
//generate token
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        return token;
    }

    //get Username From token

    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
    //validate JWT token
    public boolean validateToken(String token) throws BlogApiException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        }catch (MalformedJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        }catch (ExpiredJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        }catch (UnsupportedJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        }catch (IllegalArgumentException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }
    }
}
