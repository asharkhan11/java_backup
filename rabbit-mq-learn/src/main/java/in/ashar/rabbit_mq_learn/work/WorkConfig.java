package in.ashar.rabbit_mq_learn.work;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkConfig {

    public static final String QUEUE = "work_queue";

    @Bean("work")
    public Queue queue(){
        return new Queue(QUEUE, false);
    }

}
