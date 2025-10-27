package in.ashar.rabbit_mq_learn.direct;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectConsumer {

    @RabbitListener(queues = DirectConfig.QUEUE)
    public void receiveMessage(String message){

        System.out.println("message received : "+message);

    }

}
