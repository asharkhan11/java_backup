package in.ashar.oauth_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
@EnableWebSecurity
public class OauthClientApplication extends  Thread{

	public static void main(String[] args) {
		SpringApplication.run(OauthClientApplication.class, args);

	}



}
