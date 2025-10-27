package in.ashar.rabbit_mq_learn.pubSub;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.TimeUnit;

@Service
public class FanOutProducer {

    @Autowired
    private RabbitTemplate template;

//    @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.SECONDS)
    public void sendMessage(){

        String msg = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        template.convertAndSend(FanOutConfig.EXCHANGE,"", msg);

    }

}
