package org.example;

import org.example.components.impl.Maths;
import org.example.components.impl.Student;
import org.example.configuration.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
//        Maths math = context.getBean(Maths.class);
//        Student student = math.getStudent();
//        student.qualification();
        context.close();
    }
}
