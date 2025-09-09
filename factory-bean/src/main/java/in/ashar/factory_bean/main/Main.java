package in.ashar.factory_bean.main;

import in.ashar.factory_bean.objects.A;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("in.ashar.factory_bean")
@EnableAutoConfiguration
public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        A afb1 = (A) context.getBean("afb");
        A afb2 = (A) context.getBean("afb");

        A fb1 = (A) context.getBean("fb");
        A fb2 = (A) context.getBean("fb");

        System.out.println(afb1==afb2);
        System.out.println(fb1==fb2);

    }
}
