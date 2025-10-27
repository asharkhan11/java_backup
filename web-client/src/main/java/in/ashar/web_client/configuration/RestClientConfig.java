package in.ashar.web_client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient(){

        return RestClient.builder()
                .baseUrl("http://localhost:9090")
                .defaultHeader("rest-client","header added by rest client")
                .build();

    }

}
