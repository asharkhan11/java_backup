package com.example.learning2.db;

import com.example.learning2.entity.Receipt;
import com.example.learning2.utility.StoreOnDisk;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class FakeDb {

    @Autowired
    private StoreOnDisk storeOnDisk;
    @Autowired
    private ObjectMapper objectMapper;

   public String save(Receipt receipt){
       try {

           JsonNode jsonNode = objectMapper.convertValue(receipt, JsonNode.class);
           storeOnDisk.storeInFile(jsonNode);

       } catch (IOException e){
           log.error("error while storing in file : {}", e.getMessage());
           return "error while adding data";
       }

       return "added";
   }

   public List<Object> findAll(){
       List<Object> list;
       try {
           list = storeOnDisk.readFromFile();
       }catch (IOException e){
           log.error("error while reading from file : {}",e.getMessage());
           return new ArrayList<>();
       }

       return list;
   }

    public String save(JsonNode jsonNode) {
        try {
            storeOnDisk.storeInFile(jsonNode);
        } catch (IOException e){
            log.error("error while storing in file : {}", e.getMessage());
            return "error while adding data";
        }

        return "added";
    }
}
