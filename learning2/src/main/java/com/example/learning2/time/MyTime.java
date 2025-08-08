package com.example.learning2.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyTime {



    public void newDateTime(){
        LocalDate currLocalDate = LocalDate.now();
        LocalDate specificLocalDate = LocalDate.of(2002,9,11);

        System.out.println("curr local date : "+currLocalDate);
        System.out.println("specific local date : "+specificLocalDate);

        DateTimeFormatter f1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate localDate = LocalDate.parse("11/09/2002",f1);

        String stringLocalDate = localDate.format(f1);

        System.out.println(localDate);
        System.out.println(stringLocalDate);

        LocalDateTime currLocalDateTime = LocalDateTime.now();
        LocalDateTime specificLocalDateTime = LocalDateTime.of(2002,9,11,12,30,55);

        System.out.println("curr local date time : "+currLocalDateTime);
        System.out.println("specific local date time : "+specificLocalDateTime);

        DateTimeFormatter f2 = DateTimeFormatter.ofPattern("dd-MMM-yy hh:mm a");

        LocalDateTime localDateTime = LocalDateTime.parse("11-Sep-02 12:30 PM",f2);

        String stringLocalDateTime = LocalDateTime.now().format(f2);

        System.out.println(localDateTime);
        System.out.println(stringLocalDateTime);


    }

}
