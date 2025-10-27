package in.ashar.rabbit_mq_learn.pubSub;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanOutConfig {

    public static final String QUEUE1 = "fanout_queue_1";
    public static final String QUEUE2 = "fanout_queue_2";
    public static final String EXCHANGE = "fanout_exchange";

    @Bean("fanout1")
    public Queue queue1(){
        return new Queue(QUEUE1, false);
    }

    @Bean("fanout2")
    public Queue queue2(){
        return new Queue(QUEUE2, false);
    }

    @Bean
    public FanoutExchange exchange(){
        return new FanoutExchange(EXCHANGE, false, true);
    }

    @Bean
    public Binding binding1(@Qualifier("fanout1") Queue queue1, FanoutExchange exchange){
        return BindingBuilder.bind(queue1).to(exchange);
    }

    @Bean
    public Binding binding2(@Qualifier("fanout2") Queue queue1, FanoutExchange exchange){
        return BindingBuilder.bind(queue1).to(exchange);
    }
}
