package com.example.learning2.time;

import java.util.Date;

public class MyDate {

    public void legacyDate(){

        Date date1 = new Date();
        Date date2 = new Date();
        System.out.println("date 1 : "+date1);
        System.out.println("date 2 : "+date2);

        long time1 = date1.getTime();
        long time2 = date2.getTime();

        date1.setTime(time1 + 5000);
        date2.setTime(time2 - 5000);

        boolean after = date1.after(date2);
        boolean before = date1.before(date2);

        System.out.println("after : "+after);
        System.out.println("before : "+before);

        System.out.println(date1.toString());
        System.out.println(date2.toInstant());
        System.out.println("time1 : "+time1);





    }
}
