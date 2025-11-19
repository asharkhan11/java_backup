package in.ashar.kafka_learn.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final AdminClient adminClient;

    // Create Topic
    public void createTopic(String name, int partitions, short replicationFactor)
            throws ExecutionException, InterruptedException {
        NewTopic newTopic = new NewTopic(name, partitions, replicationFactor);
        adminClient.createTopics(Collections.singleton(newTopic)).all().get();
    }

    // List Topics
    public Set<String> listTopics() throws ExecutionException, InterruptedException {
        return adminClient.listTopics().names().get();
    }

    // Delete Topic
    public void deleteTopic(String name)
            throws ExecutionException, InterruptedException {
        adminClient.deleteTopics(Collections.singleton(name)).all().get();
    }

    // Describe Topic
    public DescribeTopicsResult describeTopic(String name){
        return adminClient.describeTopics(Collections.singleton(name));
    }

}
