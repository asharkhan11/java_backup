package daily.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CheckDuplicate {

    public static int[] checkDuplicate(int[] arr){

        List<Integer> list = new ArrayList<>();
        HashMap<Integer,Integer> map = new HashMap<>();

        for (int num : arr){
            map.put(num, map.getOrDefault(num,0)+1);
        }

        map.forEach((k,v)->{
            if(v >1) list.add(k);
        });

        return list.stream().mapToInt(Integer::valueOf).toArray();

    }
}
