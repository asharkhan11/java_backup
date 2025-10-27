package com.example.filehandling2.rest_template;

import com.example.filehandling2.exception.MyError;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class RestTemplateErrorHandler implements ResponseErrorHandler {


    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is5xxServerError() || response.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {

        byte[] bytes = response.getBody().readAllBytes();

        String data = new String(bytes, StandardCharsets.UTF_8);

        if(response.getStatusCode().is4xxClientError()){
            throw new MyError(response.getStatusText() + " : "+ data);
        }
        else if(response.getStatusCode().is5xxServerError()){
            throw new MyError(response.getStatusText() + " : "+ data);
        }

        ResponseErrorHandler.super.handleError(url, method, response);
    }
}
