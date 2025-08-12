package org.ashar;

import org.ashar.dsa.BinarySearch;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

//        StreamQuestions sq = new StreamQuestions();
//
//        sq.steamQuestions();

        BinarySearch bs = new BinarySearch();

//        int[] arr1 = {2, 3, 5, 9, 14, 16, 18};
//        System.out.println(bs.ceilingOfNumber(arr1, 1));
//        System.out.println(bs.floorOfNumber(arr1, 2));

//        char[] arr = {'c', 'd', 'g', 'j'};
//        char ans = bs.findSmallestCharacterGreaterThanTarget(arr, 'c');
//        System.out.println(ans);

        int[] arr = {5,7,7,7,7,8,8,10};
        int first = bs.findFirstAndLastOccurrence(arr, 7, true);
        int last = bs.findFirstAndLastOccurrence(arr, 7, false);

        System.out.println(" first occurrence : "+first +
                            " last occurrence : "+last);

    }
}