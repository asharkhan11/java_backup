package daily.tasks;

public class ReverseString {


    public static String reverseString(String original) {
        StringBuffer s = new StringBuffer(original);

        String r = "java";
        String res = "";

        for(int i=r.length()-1; i>=0; i--){

            res += r.charAt(i);

        }

        System.out.println(res);

        return s.reverse().toString();

    }
}
