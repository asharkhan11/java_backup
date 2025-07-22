package july.twentytwo;

import java.util.concurrent.Callable;

public class MyGenericClass<T> implements Callable<String> {

    private final T value;

    public MyGenericClass(T value){
        this.value = value;
    }

    public String myMethod(T param){
        return param.toString();
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(3000);
        return myMethod(value);
    }
}
