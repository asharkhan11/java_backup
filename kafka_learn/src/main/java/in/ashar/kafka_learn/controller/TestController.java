package in.ashar.kafka_learn.controller;

import in.ashar.kafka_learn.service.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final KafkaProducer producer;

//    @GetMapping("/send/{msg}")
//    public String send(@PathVariable String msg) {
//        producer.sendMessage(msg);
//        return "Sent: " + msg;
//    }

}
