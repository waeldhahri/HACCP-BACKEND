
package com.example.haccpbackend.registerJWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {


    private long jwtExpiration = 43200000; // 12 heures en millisecondes

    private String secretKey="eZqjuzqqLo4tElCuVjVkqPLp1gaaHGRc8x5xHBljo4kK4dhQzChJX67pBmYRM/VScICh01PhV9l5DFq02MfMWsOsvUuAKSJu7kxUO++Meqe9+9FoGwxtsnkDH0R4l6t7qHESWAJ8iEO7ptNLDVXWBVYSTvzHCOtacQFzubJuU/jBfcWIg+t0eOLioANRzebqfZVpegbkr/xToWjs5olLMXV8kMv6vURObOvOs7pGMjdl7C44lMQeh9gYXoVdT550NxXFTDJfW5mZ/XF+eWBR1ZwDgzGw4H2oq6wSf3qOel07H5COWTJ3i07WItOeM8t6DKtzsNe8tayG2qk9J9vqvxs3CCEaM0bM9cUw4sGivfc=";


    public String generateToken(Map<String,Object> claims, UserDetails userDetails){


        return buildToken(claims,userDetails,jwtExpiration);
    }



/*    private String buildToken(Map<String, Object> extraclaims,
                              UserDetails userDetails,
                              Long jwtExpiration) {


  *//*
   var authorities = userDetails.getAuthorities().stream().
                map(GrantedAuthority::getAuthority).toList();*//**//*

*//*




        Map<String, Object> claims = new java.util.HashMap<>(extraclaims);

        claims.put("sub", userDetails.getUsername());
        claims.put("iat", new Date().getTime());
        claims.put("exp", new Date(System.currentTimeMillis() + jwtExpiration).getTime());

        // Ajouter les rôles
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .toList();
        claims.put("authorities", authorities);

        //return Jwts.SIG.HS256.sign(getSignInKey(), claims);
        return Jwts.builder().setClaims(extraclaims).setSubject(userDetails.getUsername()).compact();



   *//*

        return Jwts.builder().setClaims(extraclaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .claim("authorities",authorities)
                .signWith(getSignInKey())
                .compact();*//*

    }*/





    private String buildToken(Map<String, Object> extraclaims,
                              UserDetails userDetails,
                              Long jwtExpiration) {




        var authorities = userDetails.getAuthorities().stream().
                map(GrantedAuthority::getAuthority).toList();
        return Jwts.builder().setClaims(extraclaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .claim("authorities",authorities)
                .signWith(getSignInKey())
                .compact();
    }











    public boolean isTokenValid(String token , UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);

    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());

    }


    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }




    private Key getSignInKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token , Function<Claims,T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build().parseClaimsJws(token).getBody();
    }









}

