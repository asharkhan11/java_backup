package in.ashar.feign_fallback.fallback;

import in.ashar.feign_fallback.feign.RemoteServiceClient;
import org.springframework.stereotype.Component;

@Component
public class RemoteServiceFallback implements RemoteServiceClient {
    @Override
    public String getTestString() {
        return "Fallback response";
    }
}
