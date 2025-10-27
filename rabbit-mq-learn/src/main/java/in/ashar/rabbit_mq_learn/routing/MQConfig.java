package in.ashar.rabbit_mq_learn.routing;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Bean("iPhone")
    public Queue iPhone(){
        return new Queue("iPhone",false,false, false);
    }

    @Bean("vivo")
    public Queue vivo(){
        return new Queue("vivo",false,false, false);
    }



}