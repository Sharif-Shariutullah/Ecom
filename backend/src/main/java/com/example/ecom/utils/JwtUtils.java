package com.example.ecom.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    public static final String SECRET = "5367566859703373367639792F423F4528482B4D625165546857605A713474375367566B597033733676397925";


    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);

    }

    private String createToken(Map<String, Object> claims, String userName) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();

    }


    private Key getSignKey() {
        byte[] keybytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keybytes);
    }

    ;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);

    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    ;


    private Boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim((token), Claims::getExpiration);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    } ;


}
