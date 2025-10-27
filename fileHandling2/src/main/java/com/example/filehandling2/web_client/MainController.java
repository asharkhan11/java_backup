package com.example.filehandling2.web_client;

import com.example.filehandling2.exception.MyError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;


@Slf4j
@RestController("webMainController")
@RequestMapping("/web")
@RequiredArgsConstructor
public class MainController {

    private final WebClient webClient;

    @RequestMapping(path = "/name", method = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT,RequestMethod.DELETE})
    public Mono<ResponseEntity<String>> callRemote(String name, HttpServletRequest request){

        return webClient
                .method(HttpMethod.valueOf(request.getMethod()))
                .uri(uriBuilder -> uriBuilder.path("/name")
                        .queryParam("name",name)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response->
                        response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new RuntimeException("Client error : "+body)))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response->
                        response.bodyToMono(String.class)
                                .flatMap(body-> Mono.error(new RuntimeException("Server error : "+body)))
                )
                .bodyToMono(String.class)// execute only if status is 2xx successful
                .map(ResponseEntity::ok)
                .retryWhen(
                        Retry
                                .backoff(2, Duration.ofSeconds(2)).maxBackoff(Duration.ofSeconds(10))
                                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure())
                        ) // retry mechanism
                .doOnError(e -> log.error("Error occurred : {}",e.getMessage())) // just peek the error
                .onErrorMap(e -> new MyError(e.getMessage()))   // transform the error
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body(e.getMessage()))); // catch the error


    }

}
