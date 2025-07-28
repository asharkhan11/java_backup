package july.twentyone;

import java.util.Arrays;
import java.util.List;

public class StringAndIntegerPool {

    public static void main(String[] args) {
//        String a = "hello";
//        String b ="hello";
//
//        Integer l = 128;
//        Integer m = 128;
//
//        System.out.println(l==m);
//        System.out.println(l.equals(m));

//        A aobj = new B();
//        B downcast = (B)aobj;
//        downcast.a();
//
//        B objb = new B();
//        A upcasted = objb;
//        upcasted.a();
//
//        List<Integer> l2 = Arrays.stream(new int[]{24,4363,23,}).boxed().toList();
//
//        List<Integer> result = l2.stream().sorted(
//                Comparator.comparing(n->(Integer)n
//                ).reversed()).toList();
//
//
//
//
//        float f = 2.3f;
//        int i = (int)f;
//
//        int j = 4;
//        float k = j;

        Integer[] arr = {1,2,3,4};
        List<Integer> l1 = Arrays.asList(arr);
        List<Integer> l2 = List.of(1,2,3,4);

        l1.set(0,5);


        System.out.println(l1);
        System.out.println(Arrays.toString(arr));

    }

}
class B extends A{
    public void a(){
        System.out.println("int class b");
    }
}
class A {

    public void a(){
        System.out.println("int class a");
    }
}
