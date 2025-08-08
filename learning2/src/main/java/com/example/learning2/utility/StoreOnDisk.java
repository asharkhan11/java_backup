package com.example.learning2.utility;

import com.example.learning2.entity.Receipt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class StoreOnDisk {

    private final File file = new File("C:\\Users\\jalas\\OneDrive\\Desktop\\SpringBoot\\receipt.txt");

    @Autowired
    private ObjectMapper objectMapper;

    public void storeInFile(JsonNode jsonNode) throws IOException {

        ObjectNode objectNode = (ObjectNode) jsonNode;

        List<ObjectNode> receipts = new ArrayList<>();

        if (file.exists()) {

            /// used ObjectMapper here ///
            receipts = objectMapper.readValue(file, new TypeReference<>() {});

            int size = receipts.size();
            objectNode.put("receiptId",size);

            receipts.add(objectNode);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file,receipts);

        } else {
            objectNode.put("receiptId",0);
            receipts.add(objectNode);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, receipts);
        }
    }

    public List<Object> readFromFile() throws IOException {
        return objectMapper.readValue(file, new TypeReference<>() {});
    }
}
