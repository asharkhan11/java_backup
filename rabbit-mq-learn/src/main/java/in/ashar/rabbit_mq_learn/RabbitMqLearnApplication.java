package in.ashar.rabbit_mq_learn;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RabbitMqLearnApplication {



	public static void main(String[] args) {
		SpringApplication.run(RabbitMqLearnApplication.class, args);
	}

}
