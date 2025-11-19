package in.ashar.kafka_learn.controller;

import in.ashar.kafka_learn.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {

    private final AdminClient adminClient;
    private final TopicService topicService;

    // ✅ Create a new topic
    @PostMapping("/create")
    public String createTopic(@RequestParam String name, @RequestParam(defaultValue = "1") int partitions, @RequestParam(defaultValue = "1") short replicationFactor) {
        try {
            topicService.createTopic(name, partitions, replicationFactor);
            return "✅ Topic created: " + name;
        } catch (Exception e) {
            return "❌ Error creating topic: " + e.getMessage();
        }
    }

    // ✅ List all topics
    @GetMapping("/list")
    public Set<String> listTopics()  {
        try {
            return topicService.listTopics();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // ✅ Delete a topic
    @DeleteMapping("/delete/{name}")
    public String deleteTopic(@PathVariable String name) {
        try {
            topicService.deleteTopic(name);
            return "🗑️ Topic deleted: " + name;
        } catch (Exception e) {
            return "❌ Error deleting topic: " + e.getMessage();
        }
    }

    // ✅ Get topic details (partitions, etc.)
    @GetMapping("/{name}")
    public Map<String, Object> describeTopic(@PathVariable String name) throws ExecutionException, InterruptedException {
        DescribeTopicsResult result = adminClient.describeTopics(Collections.singletonList(name));
        TopicDescription description = result.topicNameValues().get(name).get();

        Map<String, Object> info = new LinkedHashMap<>();
        info.put("topic", description.name());
        info.put("partitions", description.partitions().size());
        info.put("partitionDetails", description.partitions());
        return info;
    }

}
