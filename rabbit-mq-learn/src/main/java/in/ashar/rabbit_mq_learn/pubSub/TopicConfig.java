package in.ashar.rabbit_mq_learn.pubSub;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {

    public static final String QUEUE1 = "topic_queue_1";
    public static final String QUEUE2 = "topic_queue_2";
    public static final String EXCHANGE = "topic_exchange";
    public static final String ROUTING_KEY_MUMBAI = "city.mumbai";
    public static final String ROUTING_KEY_AMRAVATI = "city.amravati";

    @Bean("topic1")
    public Queue queue1(){
        return new Queue(QUEUE1, false);
    }

    @Bean("topic2")
    public Queue queue2(){
        return new Queue(QUEUE2, false);
    }

    @Bean("topic_exchange")
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGE, false, true);
    }

    @Bean("topic_binding_1")
    public Binding binding1(@Qualifier("topic1") Queue queue1, @Qualifier("topic_exchange") TopicExchange exchange){
        return BindingBuilder.bind(queue1).to(exchange).with("city.mumbai");
    }

    @Bean("topic_binding_2")
    public Binding binding2(@Qualifier("topic2") Queue queue2, @Qualifier("topic_exchange") TopicExchange exchange){
        return BindingBuilder.bind(queue2).to(exchange).with("city.*");
    }

}
