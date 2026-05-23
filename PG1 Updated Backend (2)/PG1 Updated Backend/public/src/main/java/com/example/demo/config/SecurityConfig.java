package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import com.example.demo.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final CustomUserDetailsService customUserDetailsService;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                return http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                // Permit preflight requests
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                                                // Public endpoints
                                                .requestMatchers("/api/users/signup").permitAll()
                                                .requestMatchers("/api/auth/login").permitAll()
                                                .requestMatchers("/api/admin/signin").permitAll()
                                                .requestMatchers("/api/public/**").permitAll()
                                                .requestMatchers("/uploads/**").permitAll()
                                                .requestMatchers("/dept-document/**").permitAll()
                                                // Allow GET requests for dropdown data (districts, taluks, etc.)
                                                .requestMatchers(HttpMethod.GET, "/api/districts/**", "/api/taluks/**")
                                                .permitAll()

                                                // (Optional) If you want complaint creation to be public
                                                // .requestMatchers(HttpMethod.POST, "/api/complaints/**").permitAll()
                                                .requestMatchers("/api/admin/**").hasAnyRole("ADMIN", "DEPARTMENT")
                                                // .requestMatchers("/api/admin/uploads/**").hasRole("ADMIN")

                                                // 4. Define DEPARTMENT-only endpoints
                                                .requestMatchers("/api/department/**").hasRole("DEPARTMENT")
                                                .requestMatchers("/api/department/assigned-complaints")
                                                .hasRole("DEPARTMENT")
                                                // .requestMatchers("/api/admin/departments").hasRole("DEPARTMENT")

                                                // All other requests need authentication
                                                .anyRequest().authenticated())
                                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                                .build();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        @SuppressWarnings("deprecation")
        @Bean
        public PasswordEncoder passwordEncoder() {
                return NoOpPasswordEncoder.getInstance(); // Use BCrypt in production!
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // React frontend
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(
                                Arrays.asList("Authorization", "Content-Type", "Accept", "X-Requested-With"));
                configuration.setAllowCredentials(true);
                configuration.setMaxAge(3600L); // cache preflight for 1 hour

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}
