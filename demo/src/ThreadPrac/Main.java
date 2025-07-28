package ThreadPrac;

import java.util.concurrent.*;


public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executorService=Executors.newFixedThreadPool(10);
        Future<String> res = executorService.submit(new MyCallable());
        System.out.println(res.get());
        executorService.shutdown();


    }
}

class MyCallable implements Callable<String>{

    public String call(){
        String str = "a";
        for(int i=0; i<10; i++){
            str += i + " ";
        }
        return str;
    }

}
