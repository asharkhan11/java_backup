package in.ashar.feign_client_learn.configuration;


import in.ashar.feign_client_learn.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/auth/**").permitAll()
                            .requestMatchers("/role/**").hasRole("ADMIN")
                            .anyRequest().hasRole("USER");
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e->{
                    e
                            .accessDeniedHandler((request, response, accessDeniedException) -> {
                               response.setContentType("application/json");
                               response.getWriter().write("""
                                       {
                                        "error":"%s"
                                       }
                                       """.formatted(accessDeniedException.getMessage()));
                            });
                })
                .build();
    }



    @Bean
    public UserDetailsService userDetailsService() {
        return username -> null; // no local user loading, rely only on JWT
    }



}
