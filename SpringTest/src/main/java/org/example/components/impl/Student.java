package org.example.components.impl;


import org.example.components.Person;
import org.springframework.stereotype.Component;

@Component
public class Student implements Person {

    @Override
    public void qualification() {
        System.out.println("graduate of engineering");
    }
}
