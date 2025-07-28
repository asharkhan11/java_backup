package daily.tasks;

public class ReverseNumber {


    public static int reverseNumber(int number) {
        int reversedNum =0;

        while (number>0){
            reversedNum = reversedNum*10 + number%10;
            number /= 10;
        }

        return reversedNum;
    }
}
