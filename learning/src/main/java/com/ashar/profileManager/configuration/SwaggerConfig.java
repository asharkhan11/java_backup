package com.ashar.profileManager.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI configSwagger()
    {
        return new OpenAPI()
                .info(new Info()
                        .title("Profile Manager")
                        .version("1.0")
                        .description("This is a customized Swagger UI for Managing Profiles of User.")
                        .contact(new Contact()
                                .name("API Support")
                                .email("asharkhan.eidiko@gmail.com")
                                .url("https://example.com")));
    }
}
