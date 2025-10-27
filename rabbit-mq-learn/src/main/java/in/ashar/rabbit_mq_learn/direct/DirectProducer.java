package in.ashar.rabbit_mq_learn.direct;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.TimeUnit;

@Component
public class DirectProducer {

    @Autowired
    private RabbitTemplate template;

//    @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.SECONDS)
    public void sendMessage(){

//        template.convertAndSend(DirectConfig.EXCHANGE, DirectConfig.ROUTING_KEY, message);
//        System.out.println("message sent !");

        String msg = "its %s now".formatted(LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)));

        template.convertAndSend(DirectConfig.QUEUE, msg);
        System.out.println("message sent !");

    }

}
