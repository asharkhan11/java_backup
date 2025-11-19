package in.ashar.feign_fallback.controller;

import in.ashar.feign_fallback.feign.RemoteServiceClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    private final RemoteServiceClient remoteServiceClient;

    public TestController(RemoteServiceClient remoteServiceClient) {
        this.remoteServiceClient = remoteServiceClient;
    }

    @GetMapping("/test")
    public String testApi() {
        return remoteServiceClient.getTestString();
    }
}
