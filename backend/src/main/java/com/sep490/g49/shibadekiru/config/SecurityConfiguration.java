package com.sep490.g49.shibadekiru.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private static final String[] WHITE_LIST_URL = {
            "/api/auth/**",
            "/api/alphabet/**",
            "/api/post/**",
            "/api/book/**",
            "/api/lesson/**",
            "api/knowledge/**",
    };

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        System.out.println("check security");
        httpSecurity
                .cors(httpSecurityCorsConfigurer -> {
                    httpSecurityCorsConfigurer
                            .configurationSource(request -> {
                                CorsConfiguration config = new CorsConfiguration();
                                config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                                config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
                                config.setAllowCredentials(true);
                                return config;
                            });
                })
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(GET, "/api/lecture/**").hasAnyRole("ADMIN", "LECTURE")
                                .requestMatchers(POST, "/api/lecture/**").hasAnyRole("ADMIN", "LECTURE")
                                .requestMatchers(PUT, "/api/lecture/**").hasAnyRole("ADMIN", "LECTURE")
                                .requestMatchers(DELETE, "/api/lecture/**").hasAnyRole("ADMIN", "LECTURE")
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )

        ;

        return httpSecurity.build();
    }
}
