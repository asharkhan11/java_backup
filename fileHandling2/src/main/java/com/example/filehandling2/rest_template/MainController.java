package com.example.filehandling2.rest_template;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController("restMainController")
@RequestMapping("/rest")
@RequiredArgsConstructor
public class MainController {

    private final RestTemplate template;

    @RequestMapping(path = "/name", method = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT,RequestMethod.DELETE})
    public ResponseEntity<String> callRemote(String name, HttpServletRequest request){

        String url = "/name";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url).queryParam("name",name);

        return template.exchange(uriBuilder.toUriString(), HttpMethod.valueOf(request.getMethod()), HttpEntity.EMPTY, String.class);
    }

}
