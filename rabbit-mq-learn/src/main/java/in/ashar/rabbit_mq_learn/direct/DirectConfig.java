package in.ashar.rabbit_mq_learn.direct;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConfig {

    public static final String QUEUE = "direct_queue";
    public static final String EXCHANGE = "direct_exchange";
    public static final String ROUTING_KEY = "direct_key";


    @Bean("direct")
    public Queue queue(){
        return new Queue(QUEUE, false);
    }

//    @Bean
//    public DirectExchange directExchange(){
//        return new DirectExchange(EXCHANGE);
//    }
//
//    @Bean
//    public Binding binding(Queue queue, DirectExchange directExchange){
//        return BindingBuilder.bind(queue).to(directExchange).with(ROUTING_KEY);
//    }

}
