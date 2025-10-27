package in.ashar.rabbit_mq_learn.pubSub;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class TopicProducer {

    @Autowired
    private RabbitTemplate template;

    private final Random random = new Random();

    @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.SECONDS)
    public void sendMessage(){

        int num = random.nextInt(10) + 1;

        if(num <= 3){
            template.convertAndSend(TopicConfig.EXCHANGE, TopicConfig.ROUTING_KEY_AMRAVATI, "Insufficient money, go to amravati");
        } else if (num <= 6) {
            template.convertAndSend(TopicConfig.EXCHANGE, TopicConfig.ROUTING_KEY_AMRAVATI, "train not available, go to amravati");
        } else {
            template.convertAndSend(TopicConfig.EXCHANGE, TopicConfig.ROUTING_KEY_MUMBAI, "Aap Mumbai aa rahe ho...");
        }

    }

}
