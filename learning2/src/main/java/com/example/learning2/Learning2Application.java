package com.example.learning2;

import com.example.learning2.time.MyTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Learning2Application {

	public static void main(String[] args) {
		SpringApplication.run(Learning2Application.class, args);

//		MyDate myDate = new MyDate();
//		myDate.legacyDate();

//		MyCalendar myCalendar = new MyCalendar();
//		myCalendar.legacyCalendar();

		MyTime time = new MyTime();

		time.newDateTime();

	}

}
