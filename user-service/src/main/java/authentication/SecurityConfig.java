package authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {



    @Bean 
    public SecurityFilterChain securityFilerChain(HttpSecurity http) throws Exception {

        // Disabling anything that might cause errors
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> 
               httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf((csrf) -> csrf.disable()).cors((cors) -> cors.disable()).httpBasic((httpBasic) -> httpBasic.disable());

        http.headers(head -> head.frameOptions(frame -> frame.disable()));

        // Update with protected paths only. The rest are permitted to be caught by auth or error controllers
        http
            .authorizeHttpRequests((authorize) -> authorize
                .anyRequest().permitAll()
        )
        .exceptionHandling(exception ->
            exception.authenticationEntryPoint((request, response, authException) -> response.sendError(401)));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }
}