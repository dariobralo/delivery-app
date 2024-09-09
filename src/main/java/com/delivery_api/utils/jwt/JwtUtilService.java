package com.delivery_api.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtUtilService {

    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    private static final int EXPIRATION_TIME = 10800000; // 3 horas
    @Value("${security.jwt.generatedBy}")
    private static String generatedBy;

    /*
     *
     */
    public String generateToken(Authentication authentication) throws Exception {
        try{
            String user = authentication.getPrincipal().toString();
            String role = authentication.getAuthorities().toString();

            return Jwts.builder()
                    .issuer(generatedBy)
                    .subject(user)
                    .claim("role", role)
                    .expiration(Date.from(Instant.now().plusMillis(EXPIRATION_TIME)))
//                    .notBefore()
                    .issuedAt(Date.from(Instant.now()))
                    .id(UUID.randomUUID().toString())
                    .signWith(SECRET_KEY)
                    .compact();

        } catch (Exception e){
            throw new Exception("Error en la generacion de token -> " ,e);
        }
    }

    public boolean validateToken(String jwt, UserDetails userDetails) throws Exception {
        try {
            String userEmail = getSubjectFromToken(jwt);

            return (userEmail.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
        } catch (Exception e) {
            throw new JwtException("Fallo en la validacion del token -> ", e);
        }
    }

    public String getSubjectFromToken(String jwt) {
        return getClaim(jwt, Claims::getSubject);
    }

    public boolean isTokenExpired(String jwt){
        return getExpiration(jwt).before(new Date());
    }

    public Date getExpiration(String jwt){
        return getClaim(jwt, Claims::getExpiration);
    }

    public <T> T getClaim(String jwt, Function<Claims, T> obtenerClaim){
        final Claims claims = getAllClaims(jwt);

        return obtenerClaim.apply(claims);
    }

    public Claims getAllClaims(String jwt){
        try {
            Claims claims = Jwts.parser().
                    verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();

            return claims;
        } catch (JwtException e){
            throw new JwtException("Error al parsear los claims en getAllClaims -> ", e);
        }
    }

}
