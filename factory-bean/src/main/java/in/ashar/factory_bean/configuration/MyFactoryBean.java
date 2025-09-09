package in.ashar.factory_bean.configuration;

import in.ashar.factory_bean.objects.A;
import org.springframework.beans.factory.FactoryBean;


public class MyFactoryBean implements FactoryBean<A> {

    private boolean isSingleton = true;
    private A obj;

    @Override
    public A getObject() throws Exception {
        if(isSingleton){
            if(obj == null){
               return obj = new A(1,"singleton");
            }
            else{
                return obj;
            }
        }
        else {
            return new A(1,"prototype");
        }
    }

    @Override
    public Class<?> getObjectType() {
        return A.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public boolean setSingleton(boolean isSingleton){
        return this.isSingleton = isSingleton;
    }
}
