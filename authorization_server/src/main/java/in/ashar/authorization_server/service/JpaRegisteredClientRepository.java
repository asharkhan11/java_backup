package in.ashar.authorization_server.service;

import in.ashar.authorization_server.entity.OAuth2Client;
import in.ashar.authorization_server.repository.OAuth2ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Primary
@RequiredArgsConstructor
public class JpaRegisteredClientRepository implements RegisteredClientRepository {

    private final OAuth2ClientRepository repository;

    @Override
    public void save(RegisteredClient registeredClient) {
        OAuth2Client client = new OAuth2Client();
        client.setId(registeredClient.getId());
        client.setClientId(registeredClient.getClientId());
        client.setClientSecret(registeredClient.getClientSecret());
        client.setRedirectUris(String.join(",", registeredClient.getRedirectUris()));
        client.setScopes(String.join(",", registeredClient.getScopes()));
        client.setGrantTypes(String.join(",", registeredClient.getAuthorizationGrantTypes()
                .stream().map(AuthorizationGrantType::getValue).toList()));
        client.setAuthenticationMethods(String.join(",", registeredClient.getClientAuthenticationMethods()
                .stream().map(ClientAuthenticationMethod::getValue).toList()));
        repository.save(client);
    }

    @Override
    public RegisteredClient findById(String id) {
        return repository.findById(id).map(this::toRegisteredClient).orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return repository.findByClientId(clientId).map(this::toRegisteredClient).orElse(null);
    }

    private RegisteredClient toRegisteredClient(OAuth2Client client) {
        return RegisteredClient.withId(client.getId())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientAuthenticationMethods(m ->
                        Arrays.stream(client.getAuthenticationMethods().split(","))
                                .forEach(v -> m.add(new ClientAuthenticationMethod(v))))
                .authorizationGrantTypes(g ->
                        Arrays.stream(client.getGrantTypes().split(","))
                                .forEach(v -> g.add(new AuthorizationGrantType(v))))
                .redirectUris(u -> u.addAll(Arrays.asList(client.getRedirectUris().split(","))))
                .scopes(s -> s.addAll(Arrays.asList(client.getScopes().split(","))))
                .build();
    }
}
