package in.ashar.oAuthLearn.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DemoController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint. No login needed!";
    }

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OidcUser oidcUser) {
        return Map.of(
            "name", oidcUser.getFullName(),
            "email", oidcUser.getEmail(),
            "roles", oidcUser.getAuthorities()
        );
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "This is the ADMIN endpoint.";
    }
}
