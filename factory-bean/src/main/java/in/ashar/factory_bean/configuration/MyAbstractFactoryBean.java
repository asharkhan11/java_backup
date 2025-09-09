package in.ashar.factory_bean.configuration;

import in.ashar.factory_bean.objects.A;
import org.springframework.beans.factory.config.AbstractFactoryBean;


public class MyAbstractFactoryBean extends AbstractFactoryBean<A> {


    @Override
    public Class<?> getObjectType() {
        return A.class;
    }

    @Override
    protected A createInstance() throws Exception {
        return new A(0,"default");
    }


}
