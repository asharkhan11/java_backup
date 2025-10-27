package com.example.filehandling2.rest_template;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(){

        return new RestTemplateBuilder()
                .rootUri("http://localhost:9090/remote")
                .connectTimeout(Duration.ofSeconds(3))
                .readTimeout(Duration.ofSeconds(5))
                .errorHandler(new RestTemplateErrorHandler())
                .interceptors(new RestTemplateInterceptor())
                .build();

    }

}
