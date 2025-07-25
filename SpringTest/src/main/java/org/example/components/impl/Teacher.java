package org.example.components.impl;


import org.example.components.Person;
import org.example.components.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class Teacher implements Person {

    @Autowired
    @Qualifier("biology")
    private Subject teach;

    @Override
    public void qualification() {
        System.out.println("Phd in Biology");
    }

    public void getTeach() {
        teach.teach();
    }


    public void setTeach(Subject teach) {
        this.teach = teach;
    }
}
