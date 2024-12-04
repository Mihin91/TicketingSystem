package lk.ac.iit.Mihin.Server.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Security configuration for H2 Console.
     * This should have higher priority to ensure it's matched first.
     */
    @Bean
    @Order(1) // Ensure this filter chain is evaluated first
    public SecurityFilterChain h2ConsoleSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/h2-console/**") // Apply this security filter to H2 Console endpoints
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll() // Allow all requests to H2 Console
                )
                .csrf(csrf -> csrf.disable()) // Disable CSRF for H2 Console
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Allow frames from same origin
                );

        return http.build();
    }

    /**
     * Security configuration for WebSocket endpoints and REST API.
     */
    @Bean
    @Order(2) // This filter chain is evaluated after the H2 Console filter chain
    public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // Enable CORS with default settings
                .csrf(csrf -> csrf.disable()) // Disable CSRF for WebSocket endpoints and APIs
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll() // Permit all requests (adjust as needed for production)
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Allow frames from same origin
                );

        return http.build();
    }
}
