package in.ashar.rabbit_mq_learn.pubSub;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TopicConsumer {

    @RabbitListener(queues = TopicConfig.QUEUE2)
    public void allDecisions(String msg){
        System.out.println("all decisions :");
        System.out.println(msg);
        System.out.println();
    }

    @RabbitListener(queues = TopicConfig.QUEUE1)
    public void goingMumbai(String msg){
        System.out.println("positive decision : "+msg);
    }

}
