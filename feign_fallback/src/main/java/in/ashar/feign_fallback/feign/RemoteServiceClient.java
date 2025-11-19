package in.ashar.feign_fallback.feign;

import in.ashar.feign_fallback.fallback.RemoteServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "remoteService", url = "http://localhost:8081", fallback = RemoteServiceFallback.class)
public interface RemoteServiceClient {
    @GetMapping("/test")
    String getTestString();
}
