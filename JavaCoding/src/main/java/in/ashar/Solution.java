package in.ashar;

import java.util.*;

class Solution {


    public static void main(String[] args) {

    }

    private List<List<Integer>> getPowerGrids(int c, int[][] connections){

        if(connections.length == 0) return null;

        List<LinkedHashSet<Integer>> powerGrids = new ArrayList<>();

        powerGrids.add(new LinkedHashSet<>(Set.of(connections[0][0], connections[0][1])));
        int listCount = 0;

        for(int i=1; i< connections.length; i++){

                if(powerGrids.get(listCount).contains(connections[i][0]) || powerGrids.get(listCount).contains(connections[i][1])){
                    powerGrids.get(listCount).add(connections[i][0]);
                    powerGrids.get(listCount).add(connections[i][1]);
                }
                else{

                }

        }

    }

    public int[] processQueries(int c, int[][] connections, int[][] queries) {

        List<Integer> res = new ArrayList<>();

        for(int i=0; i<queries.length; i++){
            for(int j=0; j<2; j++){

                if(queries[i][0] == 1){
                    res.add(queries[i][1]);
                }
                else{

                }

            }
        }

    }
}