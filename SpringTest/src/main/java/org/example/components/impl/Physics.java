package org.example.components.impl;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.example.components.Subject;
import org.springframework.stereotype.Component;

@Component
public class Physics implements Subject {

    @Override
    public void teach() {
        System.out.println("Teaches Physics");
    }

    @PostConstruct
    public void afterInitialization(){
        System.out.println("Physics object initialized through PostConstruct Annotation");
    }

    @PreDestroy
    public void beforeDestroy(){
        System.out.println("Physics object destroyed through PreDestroy Annotation");
    }
}
