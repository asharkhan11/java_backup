package in.ashar.java_mail_app.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("spring.mail.imap")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImapConfigurationProperties {

    private String host;
    private String port;
    private String user;
    private String password;
    private String protocol;
}

