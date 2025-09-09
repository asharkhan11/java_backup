package in.ashar.factory_bean;

import in.ashar.factory_bean.configuration.MyFactoryBean;
import in.ashar.factory_bean.objects.A;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FactoryBeanApplication {

	public static void main(String[] args) {
		SpringApplication.run(FactoryBeanApplication.class, args);
	}

}
