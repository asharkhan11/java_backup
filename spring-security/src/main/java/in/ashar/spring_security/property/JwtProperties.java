package in.ashar.spring_security.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
@Data
public class JwtProperties {

    private String secretKey;
    private String issuer;
    private long expiry;
}
