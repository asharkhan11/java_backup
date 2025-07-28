package daily.tasks;

import java.util.Arrays;

import static daily.tasks.ReverseArray.reverseArray;
import static daily.tasks.ReverseString.reverseString;

public class Main {

    public static void main(String[] args) {
        int number = 77208;
        int[] arr = {3,5,1,2,1,2,6};
        String original = "Hey, Ashar Khan";

        String reversedString = reverseString(original);
        System.out.println("reversed : "+reversedString);

        System.out.println("-----------------------------------------------------");

        int[] reversed = reverseArray(arr);
        System.out.println("reversed : "+ Arrays.toString(reversed));

        System.out.println("-----------------------------------------------------");

        int sum = SumOfAllArray.sumOfAllArray(arr);
        System.out.println("sum of array : "+sum);

        System.out.println("-----------------------------------------------------");

        int reversedNum = ReverseNumber.reverseNumber(number);
        System.out.println("Reversed Number : "+reversedNum);

        System.out.println("-----------------------------------------------------");

        int[] duplicates = CheckDuplicate.checkDuplicate(arr);
        System.out.println("duplicates in array : "+Arrays.toString(duplicates));

        System.out.println("-----------------------------------------------------");

        int[] primeNums = PrimeNumbersInRange.primeNumbersInRanage(0, 100);
        System.out.println(Arrays.toString(primeNums));

    }

}
