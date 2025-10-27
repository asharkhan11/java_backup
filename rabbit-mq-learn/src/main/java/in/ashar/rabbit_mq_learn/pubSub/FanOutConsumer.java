package in.ashar.rabbit_mq_learn.pubSub;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class FanOutConsumer {

    @RabbitListener(queues = FanOutConfig.QUEUE1)
    public void subscriber1(String msg){
        System.out.println("subscriber 1 : "+ msg);
    }

    @RabbitListener(queues = FanOutConfig.QUEUE2)
    public void subscriber2(String msg){
        System.out.println("subscriber 2 : "+msg);
    }

}
