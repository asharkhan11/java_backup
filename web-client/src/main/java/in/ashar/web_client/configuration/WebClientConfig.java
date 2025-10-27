package in.ashar.web_client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClientBuilder(){
        return WebClient.builder()
                .baseUrl("http://localhost:9090")
                .defaultHeader("web-client","header created by web client")
                .build();
    }

}
