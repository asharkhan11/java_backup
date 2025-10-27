package com.example.filehandling2.rest_template;

import com.example.filehandling2.exception.MyError;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        if(!request.getMethod().equals(HttpMethod.GET)){
            throw new MyError("Method not allowed. only GET method is supported");
        }

        request.getHeaders().add("my-header","some value");

        return execution.execute(request, body);
    }

}
