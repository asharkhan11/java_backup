package in.ashar.email_sender.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("spring.mail.imap")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ImapConfigurationProperties {

    private String host;
    private String port;
    private String user;
    private String password;
    private String protocol;
}
