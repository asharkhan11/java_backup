package org.example.components.impl;

import jakarta.annotation.PreDestroy;
import org.example.components.Subject;
import org.springframework.stereotype.Component;

@Component
public class Biology implements Subject{

    @Override
    public void teach() {
        System.out.println("Teaches Biology");
    }

    public void init(){
        System.out.println("Biology object initialized through init");
    }

    public void destroy(){
        System.out.println("Biology object destroyed through destroy");
    }

}
