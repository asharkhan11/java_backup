package july.twentytwo;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentHashMaps {

    public static void simpleMap() throws InterruptedException {

        HashMap<Integer, Integer> simpleMap = new HashMap<>();
        Map<Integer, Integer> map = Collections.synchronizedMap(simpleMap);

        Runnable addValues = () -> {

            for (int i = 1; i <= 180; i++) {
                    map.put(i, i);
            }
            System.out.println("values added by : "+Thread.currentThread().getName());

        };

        Runnable readValues = () -> {
            for (int k : map.keySet()) {
                System.out.println(map.get(k));
            }
        };

        Thread  write = new Thread(addValues);
        Thread  read = new Thread(readValues);

        write.start();
        read.start();

        write.join();
        read.join();

    }


    public static void synchronizedMap() {
        HashMap<Integer, Integer> map = new HashMap<>();


    }

    public static void main(String[] args) throws InterruptedException {
        simpleMap();
    }

}
