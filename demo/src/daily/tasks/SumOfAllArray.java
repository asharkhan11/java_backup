package daily.tasks;

public class SumOfAllArray {

    public static int sumOfAllArray(int[] arr){
        int sum=0;
        for(int ele : arr) sum += ele;

        return  sum;
    }
}
