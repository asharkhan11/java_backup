package in.ashar.authorization_server.controller;

import in.ashar.authorization_server.entity.OAuth2Client;
import in.ashar.authorization_server.repository.OAuth2ClientRepository;
import in.ashar.authorization_server.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final OAuth2ClientRepository repository;

    @RequestMapping(path = "/create", method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String, String> createClient(@RequestParam String redirectUri,
                                            @RequestParam String scope) {
        var client = clientService.createRandomClient(redirectUri, scope);
        return Map.of(
                "clientId", client.getClientId(),
                "clientSecret", client.getClientSecret().replace("{noop}", ""),
                "redirectUri", redirectUri,
                "scope", scope
        );
    }

    @GetMapping
    public List<OAuth2Client> getAllClients() {
        return repository.findAll();
    }
}