package com.tamimSoft.fakeStore.config;

import com.tamimSoft.fakeStore.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;


    /**
     * Configures the security filter chain for Spring Security.
     * This method defines how to handle security for different HTTP requests.
     *
     * @param http The HttpSecurity instance used to configure the web security.
     * @return A SecurityFilterChain instance representing the configured security filter chain.
     * @throws Exception If an exception occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configure request authorization rules
        return http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Admin-related requests require ADMIN role
                        .requestMatchers("/user/**").hasRole("USER") // User-related requests require USER role
                        .anyRequest().permitAll()) // Other requests are allowed without authentication
                // Configure session management to be stateless
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Add JWT filter before the UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(Customizer.withDefaults()) // Enable CORS
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection
                .build();
    }


    /**
     * Configures the AuthenticationManager bean.
     * <p>
     * This method retrieves the AuthenticationManager from the provided AuthenticationConfiguration instance.
     * It is primarily used in Spring Security configurations to handle authentication requests.
     *
     * @param authConfig The AuthenticationConfiguration instance used to configure the authentication manager.
     * @return An AuthenticationManager instance for handling authentication requests.
     * @throws Exception If an error occurs while retrieving the authentication manager from the configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    /**
     * Configures and returns a password encoder instance.
     * This method is annotated with @Bean, indicating that the returned object will be registered as a bean in the Spring application context.
     * BCryptPasswordEncoder is chosen as the password encoder due to its robustness in securely handling user passwords.
     * BCrypt is a strong hashing function suitable for securely storing passwords.
     *
     * @return An instance of PasswordEncoder, used for encoding and comparing user passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
