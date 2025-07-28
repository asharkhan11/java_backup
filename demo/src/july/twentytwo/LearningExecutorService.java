package july.twentytwo;

import custom.exceptions.StudentEntity;

import java.util.List;
import java.util.concurrent.*;

public class LearningExecutorService {


    public static void main(String[] args) throws InterruptedException, ExecutionException {

        //using Executor
//        Executor ex1 = Executors.newFixedThreadPool(5);
//
//        ex1.execute(()->{
//            System.out.println("runnable task called by Executor");
//        });

        //using ExecutorService

        ExecutorService ex2 = Executors.newFixedThreadPool(4);
        ExecutorService ex3 = Executors.newSingleThreadExecutor();
        ScheduledExecutorService shex = Executors.newScheduledThreadPool(5);
        ExecutorService cachedEx = Executors.newCachedThreadPool();

        MyGenericClass<StudentEntity> studentObj = new MyGenericClass<>(new StudentEntity(1, "ash", 22));
        MyGenericClass<Double> doubleObj = new MyGenericClass<>(46.8);
        MyGenericClass<Integer> intObj = new MyGenericClass<>(82);

//        Future<String> res1 = ex2.submit(studentObj);
//        Future<String> res2 = ex2.submit(doubleObj);
//        Future<String> res3 = ex2.submit(intObj);

        Runnable task = () -> {
            try {
                Thread.sleep(1000);
                System.out.println("Scheduled work done by : " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                System.out.println("Interrupted : " + e.getMessage());
            }
        };

//        shex.scheduleAtFixedRate(task,3,4, TimeUnit.SECONDS);
//        shex.awaitTermination(5, TimeUnit.SECONDS);

        for (int i = 0; i < 20; i++) {
//            cachedEx.submit(task);
            ex2.submit(task);
        }

        cachedEx.shutdown();


//        ex2.shutdown();
//        System.out.println(ex2.isShutdown());

//        Thread.sleep(100);
//        System.out.println(ex2.isTerminated());

//        if(ex2.awaitTermination(3,TimeUnit.SECONDS)){
//            System.out.println("terminated");
//            System.out.println(res1.isDone() + " " + res2.isDone() + " " + res3.isDone());
//            System.out.println(res1.isCancelled() + " " + res2.isCancelled() + " " + res3.isCancelled());
//        }else{
//            res3.cancel(true);
//            ex2.shutdownNow();
//            System.out.println("not terminated, thus shutdown forcefully");
//            System.out.println(res1.isDone() + " " + res2.isDone() + " " + res3.isDone());
//            System.out.println(res1.isCancelled() + " " + res2.isCancelled() + " " + res3.isCancelled());
//        }
//        System.out.println("---------------------------------");
//        System.out.println(res1.get());
//        System.out.println(res2.get());
//        System.out.println( !res3.isCancelled()?res3.get():"not done");
//
//
//        System.out.println("------------------------------");

//        String resFromInvoke = ex3.invokeAny(List.of(studentObj, intObj, doubleObj));
//        System.out.println(resFromInvoke);

//        ex3.shutdown();


    }
}



