package in.ashar.rabbit_mq_learn.work;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class WorkConsumer {

    @RabbitListener(queues = WorkConfig.QUEUE)
    public void worker1(String msg){
        System.out.println("worker 1 : "+ msg);
    }



    @RabbitListener(queues = WorkConfig.QUEUE)
    public void worker2(String msg){
        System.out.println("worker 2 : "+ msg);
    }
}
