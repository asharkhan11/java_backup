package in.ashar.resource_server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class ResourceController {

    @GetMapping("/public")
    public String publicApi(){
        log.info("public url called");
        return "this is public api";
    }

    @GetMapping("/protected")
    public String protectedApi(Jwt jwt){
        log.info("protected url called : {}",jwt.getClaims());
        return "Hello "+ jwt.getClaimAsString("preferred_username");
    }
}
