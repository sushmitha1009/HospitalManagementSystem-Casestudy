package com.example.demo.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    // 🔒 Encode passwords using BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ Configure auth provider with custom userDetailsService
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // 🔐 Required to authenticate using email/password
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        org.springframework.web.cors.UrlBasedCorsConfigurationSource source =
                new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // 🔐 Main Security Filter Chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // 🔓 Public endpoints
                .requestMatchers(HttpMethod.POST, "/admin/add").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/login/admin").permitAll()
                .requestMatchers(HttpMethod.POST, "/doctor/add").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/login/doctor").permitAll()
                .requestMatchers(HttpMethod.POST, "/patient/add").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/login/patient").permitAll()
                .requestMatchers(HttpMethod.POST, "/admin/add-doctor").permitAll()
                .requestMatchers(HttpMethod.POST, "/admin/add-patient").permitAll()


                // ✅ Patient-protected endpoints
                .requestMatchers(HttpMethod.PUT, "/patient/update/**").hasAuthority("ROLE_PATIENT")
                .requestMatchers(HttpMethod.DELETE, "/patient/delete/**").hasAuthority("ROLE_PATIENT")
                .requestMatchers(HttpMethod.POST, "/appointment/book").hasAuthority("ROLE_PATIENT")
                
                .requestMatchers(HttpMethod.POST, "/report/upload").hasAuthority("ROLE_DOCTOR")

                // ✅ Allow preflight (CORS) requests
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // 🔐 Any other request requires authentication
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) ->
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
            )
            .build();
    }
}
