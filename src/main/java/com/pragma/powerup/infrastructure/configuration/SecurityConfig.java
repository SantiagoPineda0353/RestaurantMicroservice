package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.infrastructure.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private static final String ROL2="PROPIETARIO";
    private static final String ROL4="CLIENTE";
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST , "/api/v1/restaurants/**").hasRole("ADMINISTRADOR")
                    .antMatchers(HttpMethod.POST , "/api/v1/dishes/**").hasRole(ROL2)
                    .antMatchers(HttpMethod.PATCH , "/api/v1/dishes/**").hasRole(ROL2)
                    .antMatchers(HttpMethod.GET , "/api/v1/restaurants/*").permitAll()
                    .antMatchers(HttpMethod.PATCH , "/api/v1/dishes/*/status").hasRole(ROL2)
                    .antMatchers(HttpMethod.GET , "/api/v1/restaurants/").hasRole(ROL4)
                    .antMatchers(HttpMethod.GET , "/api/v1/dishes/restaurant/**").hasRole(ROL4)
                    .antMatchers(HttpMethod.GET , "/api/v1/orders/").hasRole("EMPLEADO")
                    .antMatchers(HttpMethod.POST , "/api/v1/orders/**").hasRole(ROL4)
                    .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
