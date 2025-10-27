package com.example.filehandling2.web_client;

import io.netty.channel.ChannelOption;
import io.netty.handler.logging.LogLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.time.Duration;

@Configuration
public class WebClientConfig {

    public ExchangeFilterFunction requestInterceptor(){
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {

            clientRequest.headers().add("test-header","test value");

            return Mono.just(clientRequest);
        });
    }

    @Bean
    public WebClient webClient(){

        HttpClient client = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(5)) // response timeout
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,3000) // connection timeout
                .wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL); // for logging purpose

        return WebClient.builder()
                .baseUrl("http://localhost:9090/remote")
                .clientConnector(new ReactorClientHttpConnector(client))
                .filter(requestInterceptor())
                .build();
    }

}
