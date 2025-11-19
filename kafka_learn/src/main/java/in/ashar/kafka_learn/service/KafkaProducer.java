package in.ashar.kafka_learn.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

//    public void sendMessage(String msg) {
//        kafkaTemplate.send("my-topic", msg);
//    }

}
