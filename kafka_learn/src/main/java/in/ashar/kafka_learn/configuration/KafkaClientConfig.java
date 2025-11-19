package in.ashar.kafka_learn.configuration;

import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaClientConfig {

    private AdminClient adminClient;

    @Bean
    public AdminClient adminClient() {
        Map<String, Object> config = new HashMap<>();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        this.adminClient = AdminClient.create(config);
        return this.adminClient;
    }

    @PreDestroy
    public void closeAdminClient() {
        if (adminClient != null) {
            adminClient.close();
            System.out.println("Kafka AdminClient closed successfully.");
        }
    }
}
