package in.ashar.authorization_server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "oauth2_registered_client")
@Data @NoArgsConstructor @AllArgsConstructor
public class OAuth2Client {
    @Id
    private String id;
    @Column(unique = true, nullable = false)
    private String clientId;
    @Column(nullable = false)
    private String clientSecret;
    private String redirectUris;
    private String scopes;
    private String grantTypes;
    private String authenticationMethods;
}
