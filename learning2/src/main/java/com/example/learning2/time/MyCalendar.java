package com.example.learning2.time;

import java.util.Calendar;

public class MyCalendar {


    public void legacyCalendar() {

        Calendar calendar1 = Calendar.getInstance();

        System.out.printf("%d-%d-%d",calendar1.get(Calendar.YEAR),calendar1.get(Calendar.MONTH)+1,calendar1.get(Calendar.DAY_OF_MONTH));

    }
}
