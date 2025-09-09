package in.ashar.factory_bean.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean("fb")
    public MyFactoryBean getFactoryBean(){

        MyFactoryBean fb = new MyFactoryBean();
        fb.setSingleton(false);
        return fb;

    }

    @Bean("afb")
    public MyAbstractFactoryBean getAbstractFactoryBean(){
        MyAbstractFactoryBean afb = new MyAbstractFactoryBean();
        afb.setSingleton(true);

        return afb;
    }

}
