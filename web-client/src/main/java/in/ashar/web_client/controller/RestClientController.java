package in.ashar.web_client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/rest-client")
public class RestClientController {

    @Autowired
    private RestClient restClient;


    @GetMapping("/test/{test}")
    public String getTest(@PathVariable String test) {

        String url = "/test/" + test;

        try {

            return restClient
                    .get()
                    .uri(url)
                    .retrieve()
                    .body(String.class);

        } catch (HttpClientErrorException e) {
            return "Client error : " + e.getMessage();
        } catch (HttpServerErrorException e) {
            return "Server error : " + e.getMessage();
        } catch (ResourceAccessException e) {
            return "I/O error : " + e.getMessage();
        }

    }

    @GetMapping("/test/{test}/entity")
    public String getTestUsingEntity(@PathVariable String test) {

        String url = "/test/{test}";

        try {

            return restClient
                    .get()
                    .uri(url,test)
                    .retrieve()
                    .toEntity(String.class)
                    .getBody();

        } catch (HttpClientErrorException e) {
            return "Client error : " + e.getMessage();
        } catch (HttpServerErrorException e) {
            return "Server error : " + e.getMessage();
        } catch (ResourceAccessException e) {
            return "I/O error : " + e.getMessage();
        }
    }

    @PostMapping("/test")
    public String postTest(@RequestBody String test) {

        String url = "/test";


        try {

            return restClient
                    .post()
                    .uri(url)
                    .body(test)
                    .retrieve()
                    .body(String.class);


        } catch (HttpClientErrorException e) {
            return "Client error : " + e.getMessage();
        } catch (HttpServerErrorException e) {
            return "Server error : " + e.getMessage();
        } catch (ResourceAccessException e) {
            return "I/O error : " + e.getMessage();
        }

    }


    @GetMapping("/test/{test}/header")
    public String getWithHeaderCall(@PathVariable String test, @RequestHeader int num) {

        String url = "/test/{test}/header";

        HttpHeaders headers = new HttpHeaders();
        headers.add("num", String.valueOf(num));

        String body = "body";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);


        try {

            return restClient
                    .get()
                    .uri(url,test)
                    .header("num",String.valueOf(num))
                    .retrieve()
                    .body(String.class);


        } catch (HttpClientErrorException e) {
            return "Client error : " + e.getMessage();
        } catch (HttpServerErrorException e) {
            return "Server error : " + e.getMessage();
        } catch (ResourceAccessException e) {
            return "I/O error : " + e.getMessage();
        }

    }


    @PutMapping("/test/{test}")
    public String putTest(@PathVariable String test, @RequestBody String body) {

        String url = "/test/" + test;

        HttpEntity<String> entity = new HttpEntity<>(body);

        try {

            return restClient
                    .put()
                    .uri(url)
                    .body(body)
                    .retrieve()
                    .body(String.class);


        } catch (HttpClientErrorException e) {
            return "Client error : " + e.getMessage();
        } catch (HttpServerErrorException e) {
            return "Server error : " + e.getMessage();
        } catch (ResourceAccessException e) {
            return "I/O error : " + e.getMessage();
        }

    }

    @DeleteMapping("/test/{test}")
    public String deleteTest(@PathVariable String test) {
        String url = "/test/" + test;

        try {

            String body = restClient
                    .delete()
                    .uri(url)
                    .retrieve()
                    .body(String.class);

            System.out.println(body);


        } catch (HttpClientErrorException e) {
            return "Client error : " + e.getMessage();
        } catch (HttpServerErrorException e) {
            return "Server error : " + e.getMessage();
        } catch (ResourceAccessException e) {
            return "I/O error : " + e.getMessage();
        }

        return "DELETE processed";
    }

}
