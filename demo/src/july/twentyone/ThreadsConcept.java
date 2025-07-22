package july.twentyone;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadsConcept {


    public static void main(String[] args) throws InterruptedException {
        SharedClass sc = new SharedClass();

        DoWork t1 = new DoWork(sc, "T1");
        DoWork t2 = new DoWork(sc, "T2");


        t1.start();
        t2.start();

        t1.join();
        t2.join();

//        Map<Thread, StackTraceElement[]> o = Thread.getAllStackTraces();
//        List<Integer> l = new ArrayList<>();
//        Map<Boolean,List<Integer>> m = l.stream().collect(Collectors.partitioningBy(n-> n%2==0));
//        o.forEach((k,v)->{
//            System.out.println(k);
//        });

        System.out.println(sc.getSharedVariable());

    }

}

class SharedClass {

    private int sharedVariable = 0;
    private Lock lock;
    private boolean inUse =false;
    SharedClass() {
        lock = new ReentrantLock(true);
    }

    public void increment() {

        //using syncronized keyword
        synchronized (this) {

            sharedVariable += 1;

        }
        System.out.println("synchronized block called : "+ Thread.currentThread().getName());
        //using lock.lock()
//        lock.lock();
//        try {
//            System.out.println("incrementing through : "+ Thread.currentThread().getName());
//            Thread.sleep(1000);
//            sharedVariable += 1;
//
//        } catch (InterruptedException e) {
//            System.out.println("Interrupt occured");
//        } finally {
//            System.out.println("released lock : "+Thread.currentThread().getName());
//            lock.unlock();
//        }


        //using lock.trylock()
//        try {
//
//            if (lock.tryLock(2, TimeUnit.SECONDS)) {
//                inUse = true;
//                System.out.println("incrementing through : " + Thread.currentThread().getName());
//                Thread.sleep(1000);
//                sharedVariable += 1;
//
//            } else {
//                inUse =false;
//                System.out.println("tried, but in use : " + Thread.currentThread().getName());
//            }
//        } catch (InterruptedException e) {
//            System.out.println("Interrupt occured");
//        } finally {
//            System.out.println("released lock : "+Thread.currentThread().getName());
//            if(inUse) lock.unlock();
//        }


        //lock.lockInterruptibly()
//
//        try {
//        lock.lockInterruptibly();
//        System.out.println("incrementing through : " + Thread.currentThread().getName());
//        Thread.sleep(1000);
//        sharedVariable +=1;
//        } catch (InterruptedException e) {
//            System.out.println("Thread Interrupted : "+ Thread.currentThread().getName());
//        }
//        finally {
//            System.out.println("released lock : "+Thread.currentThread().getName());
//            lock.unlock();
//        }




    }

    public int getSharedVariable() {
        return sharedVariable;
    }
}


class DoWork extends Thread {

    SharedClass obj;

    DoWork(SharedClass obj, String name) {
        super(name);
        this.obj = obj;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            obj.increment();
        }
    }


}
