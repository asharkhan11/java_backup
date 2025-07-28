package org.example.components.impl;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.example.components.Person;
import org.example.components.Subject;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Maths implements Subject, InitializingBean, DisposableBean {


    private Student student;


    @Autowired
    public void setStudent(Student student) {
        this.student =  student;
    }

    public Student getStudent() {
        return student;
    }


    @Override
    public void teach() {
        System.out.println("Teaches Maths");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Maths object initialized through InitializingBean Interface");
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("Maths object destroyed through DisposableBean Interface");
    }


}
