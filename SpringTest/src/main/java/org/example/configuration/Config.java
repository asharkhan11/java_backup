package org.example.configuration;


import org.example.components.impl.Biology;
import org.example.components.impl.Maths;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
@ComponentScan("org.example.components")
public class Config {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Biology biology(){
        return new Biology();
    }

}
