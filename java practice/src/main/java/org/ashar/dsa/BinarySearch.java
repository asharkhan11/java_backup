package org.ashar.dsa;

public class BinarySearch {

    //find ceiling of a number

    public int ceilingOfNumber(int[] arr, int target){

        if(target> arr[arr.length-1]) return -1;

//        if(target == arr[arr.length-1]) return target;

        int s=0, e= arr.length-1;

        while (s<=e){
            int m = s + (e-s)/2;

            if(target < arr[m]){
                e = m-1;
            }
            else if(target == arr[m]){
                return arr[m];
            }
            else {
                s = m+1;
            }
        }

        return arr[s];
    }

    //find floor of a number

    public int floorOfNumber(int[] arr, int target){

        if(target< arr[0]) return -1;

        int s=0, e=arr.length-1;

        while (s<=e){
            int m = s + (e-s)/2;

            if(target < arr[m]){
                e = m-1;
            }
            else{
                s = m+1;
            }
        }

        return arr[e];
    }

    // find the smallest character which is greater than target character

    public char findSmallestCharacterGreaterThanTarget(char[] arr, char target){

        int s=0, e=arr.length-1;


        while (s<=e){
            int m = s + (e-s)/2;

            if(target < arr[m]){
                e = m-1;
            }
            else{
                s = m+1;
            }
        }
            return arr[s % arr.length];

    }

    // find first and last index of target

    public int findFirstAndLastOccurrence(int[] arr, int target, boolean findFirst){
        int s=0, e=arr.length-1;

        while(s<=e){
            int m= s+(e-s)/2;

            if(target < arr[m]){
                e = m-1;
            }
            else if(target > arr[m]){
                s = m+1;
            }
            else{
                if (findFirst){
                    e = m-1;
                }
                else {
                    s = m+1;
                }
            }
        }

        if(findFirst){
            return s;
        }else{
            return e;
        }
    }

}
