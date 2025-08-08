package com.example.learning2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {
    private int receiptId;
    private String clientName;
    private long amount;
    private final ZonedDateTime dateOfPurchase = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
}
