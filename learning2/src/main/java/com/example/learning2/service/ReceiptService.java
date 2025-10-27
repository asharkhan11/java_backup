package com.example.learning2.service;

import com.example.learning2.db.FakeDb;
import com.example.learning2.entity.Receipt;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class ReceiptService {

    @Autowired
    private FakeDb fakeDb;

    public String addReceipt(Receipt receipt) {
        return fakeDb.save(receipt);
    }

    public List<Object> getAllReceipts() {
        return fakeDb.findAll();
    }

    public String addReceipt(JsonNode jsonNode) {
        return fakeDb.save(jsonNode);
    }


    public void anyMethod() {

        Thread mythread = new Thread();


    }


}
