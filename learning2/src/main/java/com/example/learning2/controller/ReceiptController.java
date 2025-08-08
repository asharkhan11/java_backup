package com.example.learning2.controller;

import com.example.learning2.dto.ReceiptDto;
import com.example.learning2.entity.Receipt;
import com.example.learning2.service.ReceiptService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/receipt")
public class ReceiptController {


    @Autowired
    private ReceiptService receiptService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/modelMapper")
    public String createReceiptUsingModelMapper(@RequestBody ReceiptDto receiptDto){

        /// used ModelMapper here ///
        Receipt receipt = modelMapper.map(receiptDto, Receipt.class);
        return receiptService.addReceipt(receipt);

    }

    @PostMapping("/objectMapper")
    public String createReceiptUsingObjectMapper(@RequestBody ReceiptDto receiptDto){

        /// used ObjectMapper here ///
        Receipt receipt = objectMapper.convertValue(receiptDto, Receipt.class);
        return receiptService.addReceipt(receipt);

    }

    @PostMapping("/unknown")
    public String createReceipt(@RequestBody String unknownObject){

        try {
            JsonNode jsonNode = objectMapper.readTree(unknownObject);

            JsonNode jsonNode1 = jsonNode.get("clientName");
            JsonNode jsonNode2 = jsonNode.get("amount");

            if(jsonNode1 == null || jsonNode2 == null){
                throw new RuntimeException("clientName or amount not found");
            }

            return receiptService.addReceipt(jsonNode);

        } catch (JsonProcessingException e) {
            log.error("json processing error : {}",e.getMessage());
            return "error while parsing unknown object";
        }
        catch (RuntimeException e){
            log.error("required fields not found : {}",e.getMessage());
            return "Required fields not found";
        }

    }

    @GetMapping
    public List<Object> getAllReceipts() {
        return receiptService.getAllReceipts();
    }
}
