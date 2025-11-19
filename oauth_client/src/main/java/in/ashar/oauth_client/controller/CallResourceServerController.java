package in.ashar.oauth_client.controller;

import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class CallResourceServerController {

    static final String URL = "http://localhost:9090/api";
    final RestTemplate restTemplate = new RestTemplate();

    private final OAuth2AuthorizedClientService clientService;

    public CallResourceServerController(OAuth2AuthorizedClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/public")
    public String publicApi() {
        String url = URL + "/public";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
        return response.getBody();

    }

    @GetMapping("/protected")
    public String protectedApi(Authentication authentication) {
        String url = URL + "/protected";

        // ---------------------------------------------------------------------------------------------- //

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication; // auth token of authorized user after successful login from Google

        String registrationId = authToken.getAuthorizedClientRegistrationId();// the client is Google (check application.yml file)

        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(registrationId, authToken.getName()); // 'OAuth2AuthorizedClientService' stores all logged-in users, so retrieving the client object matching this name: 'authToken.getName()' and registrationId: 'google'

        String token = client.getAccessToken().getTokenValue();  // got JWT token

        // ---------------------------------------------------------------------------------------------- //

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();

    }

}
