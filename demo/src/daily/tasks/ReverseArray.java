package daily.tasks;


public class ReverseArray {

    public static int[] reverseArray(int[] arr){

//        List<Integer> reversed = Arrays.stream(arr).boxed().toList().reversed();

        int s=0,e=arr.length-1;

        while (s<e){
            int temp = arr[s];
            arr[s] = arr[e];
            arr[e] = temp;
            s++;
            e--;
        }

        return arr;

    }
}
