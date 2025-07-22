package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();
        Student s = new Student(1,"ash",11);

        try {

            String jsonString = mapper.writeValueAsString(s);
            System.out.println(jsonString);

            Student student = mapper.readValue(jsonString, Student.class);
            System.out.println(student);

        } catch (JsonProcessingException e) {
            System.out.println("error while converting");
        }


    }
}