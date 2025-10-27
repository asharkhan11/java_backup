package in.ashar.oAuthLearn.configuration;

import in.ashar.oAuthLearn.security.CustomOidcUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomOidcUserService oidcUserService) throws Exception{

         http
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/public/**").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated())

                .oauth2Login(oauth ->
                        oauth
                                .userInfoEndpoint( userInfo ->
                                        userInfo
                                                .oidcUserService(oidcUserService)
                                )
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/").permitAll()
                );

         return http.build();

    }

}
