package daily.tasks;

import java.util.ArrayList;
import java.util.List;

public class PrimeNumbersInRange {
    public static int[] primeNumbersInRanage(int m, int n) {
        List<Integer> l = new ArrayList<>();
        if(m>n) throw new RuntimeException("invalid range");

        for (int i=m; i<=n; i++){
            if(isPrime(i)){
                l.add(i);
            }
        }

        return l.stream().mapToInt(Integer::intValue).toArray();
    }

    private static boolean isPrime(int n) {
        if(n<=1) return false;

        int count =0;

        for(int i=1; i<= Math.sqrt(n); i++){
            if(n%i==0) count++;
        }

        return count < 2;
    }
}
