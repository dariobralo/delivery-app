package com.delivery_api.utils.jwt;

import com.delivery_api.utils.enums.RoleEnum;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtilService jwtUtilService;
    private final UserDetailsService userDetailsService;

    private String jwt = null;
    private String userEmail = null;
    private Claims claims = null;

    /*
     *
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);


        // Validar el authHeader
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        // Extraer el token
        jwt = authHeader.substring(7);
        userEmail = jwtUtilService.getSubjectFromToken(jwt);
        claims = jwtUtilService.getAllClaims(jwt);

        // Verificacion que el usuario no este ya autenticado
        if (userEmail!= null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            try {
                if (jwtUtilService.validateToken(jwt, userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    new WebAuthenticationDetailsSource().buildDetails(request);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } catch (Exception e) {
                throw new RuntimeException("Error en validacion de token en el filter -> ", e);
            }

        }
        filterChain.doFilter(request, response);
    }

    public boolean isAdmin(){
        return RoleEnum.ROLE_ADMIN.toString().equalsIgnoreCase((String) claims.get("role"));
    }

    public  boolean isCustomer(){
        return RoleEnum.ROLE_CUSTOMER.toString().equalsIgnoreCase((String) claims.get("role"));
    }

    public  boolean isRestaurantOwner(){
        return RoleEnum.ROLE_RESTAURANT_OWNER.toString().equalsIgnoreCase((String) claims.get("role"));
    }

    public  boolean isDev(){
        return RoleEnum.ROLE_DEVELOPER.toString().equalsIgnoreCase((String) claims.get("role"));
    }

    public String getCurrentUser(){
        return userEmail;
    }

}
