package in.ashar.java_mail_app;

import in.ashar.java_mail_app.utility.ImapConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableConfigurationProperties(ImapConfigurationProperties.class)
public class JavaMailAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaMailAppApplication.class, args);
	}

}
