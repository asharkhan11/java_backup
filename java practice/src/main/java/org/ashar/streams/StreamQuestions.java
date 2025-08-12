package org.ashar.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class StreamQuestions {

    public void steamQuestions(){

        int[] arr1 = {9,5,5,7,3,8,4,5,2,1,6,4};
        String[] arr2 = {"Alice", "Bob", "Annie", "Alex", "alien", "anabelle", "John" ,"Charlie"};
        String word = "management";

        System.out.println("Original Array : ");
        Arrays.stream(arr1).forEach(n-> System.out.print(n+ " "));
        System.out.println("\n");

        //filter even numbers

        List<Integer> list = Arrays.stream(arr1).boxed().filter(n -> n % 2 == 0).toList();
        System.out.println(list);

        // maximum in array

        Integer max = Arrays.stream(arr1).boxed().max(Integer::compareTo).get();
        System.out.println(max);

        //sort array in ascending order

        List<Integer> list1 = Arrays.stream(arr1).sorted().boxed().toList();
        System.out.println(list1);

        //sort array in descending order

        List<Integer> list2 = Arrays.stream(arr1).boxed().sorted(Comparator.reverseOrder()).toList();
        System.out.println(list2);

        //  count string with specific prefix

        long countA = Arrays.stream(arr2).filter(n -> n.toUpperCase().startsWith("A")).count();
        System.out.println(countA);


        //get name starting with specific letter and has maximum length
        String s = Arrays.stream(arr2).filter(n -> n.toUpperCase().startsWith("A")).max(String::compareTo).orElseGet(() -> "not found");
        System.out.println(s);


        // find first non-repeating character in string

        char c1 = (char) word.chars().filter(c -> word.indexOf(c) == word.lastIndexOf(c)).findFirst().orElseGet(() -> 0);
        System.out.println(c1);

    }
}
