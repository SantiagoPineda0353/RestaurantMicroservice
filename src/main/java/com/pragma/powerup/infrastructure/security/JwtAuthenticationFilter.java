package com.pragma.powerup.infrastructure.security;


import com.pragma.powerup.domain.model.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(authHeader==null|| !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String token =authHeader.substring(7);

        if (jwtUtil.isTokenValid(token)&&SecurityContextHolder.getContext().getAuthentication()==null){
            Long userId =jwtUtil.extractUserId(token);
            Long idRole= jwtUtil.extractIdRole(token);
            Long idRestaurant=jwtUtil.extractIdRestaurant(token);
            RoleEnum role = RoleEnum.fromId(idRole);
            AuthenticatedUser authenticatedUser=new AuthenticatedUser(userId,idRestaurant);
            var authorities = List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
            var authToken =new UsernamePasswordAuthenticationToken(authenticatedUser,null,authorities);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request,response);
    }
}
