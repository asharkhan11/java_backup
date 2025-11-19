//package in.ashar;
//
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class Main {
//
//    public int[] findXSum(int[] nums, int k, int x) {
//
//        int[] answer = new int[nums.length-k+1];
//
//        for(int i=0; i<nums.length-k+1; i++){
//
//            int[] subArr = Arrays.copyOfRange(nums,i,i+k);
//            Map<Integer, Long> collect = Arrays.stream(subArr).boxed().collect(Collectors.groupingBy(n -> n, Collectors.counting()));
//
//        }
//    }
//
//    public static void main(String[] args) {
//
////        Employee e1 = new Employee("a","IT",1200);
////        Employee e2 = new Employee("b","IT",1100);
////        Employee e3 = new Employee("c","HR",2200);
////        Employee e4 = new Employee("d","HR",1500);
////        Employee e5 = new Employee("e","HR",1500);
////        Employee e6 = new Employee("f","ADMIN",5500)
//
////        List<Employee> employees = List.of(e1,e2,e3,e4,e5,e6);
//
////        Map<Integer, Integer> sortedByValue = sortedByKey.entrySet()
////                .stream()
////                .sorted(Map.Entry.comparingByValue())
////                .collect(Collectors.toMap(
////                        Map.Entry::getKey,
////                        Map.Entry::getValue,
////                        (a, b) -> a,
////                        LinkedHashMap::new
////                ));
////
////        long x = 2;
////        Integer reduce = sortedByValue.entrySet().stream().limit(x).map(e -> e.getKey()*e.getValue()).reduce((a, b) -> a+b).get();
////
////
////        List<Employee> list = employees.stream().sorted((s1, s2) -> s2.salary - s1.salary).limit(3).toList();
////
////        System.out.println(list);
//
//
////        String str = "Java Stream API";
////// Count vowels (a, e, i, o, u)
//
////
////        Stream<Character> cStream = str.replaceAll("[ ]","").chars().mapToObj(c -> (char) c);
////
////        Map<Boolean, List<Character>> collect = cStream.collect(Collectors.partitioningBy(c -> "aeiou".contains(c + "")));
////
////        System.out.println(collect.get(true));
////        System.out.println(collect.get(false));
//
//
////        String str = "programming";
////// Output: "progamin"
////
////        LinkedHashSet<Character> chars = new LinkedHashSet<>();
////        for(char c : str.toCharArray()){
////            chars.add(c);
////        }
////
////    }
//
//
//        TreeMap<Integer, String> treeMap = new TreeMap<>((e1, e2) -> Integer.compare(e2, e1));
//
//        treeMap.put(5, "five");
//        treeMap.put(2, "two");
//        treeMap.put(1, "one");
//        treeMap.put(3, "three");
//        treeMap.put(4, "four");
//        treeMap.put(-1, "minus one");
//        treeMap.put(0, "zero");
//
//        System.out.println(treeMap);
//
//        int[] arr = {1, 2, 3, 5};
//
//        List<int[]> arr1 = List.of(arr);
//
//    }
//
//
//}
//
//
//class Employee {
//
//    String name;
//    String dept;
//    int salary;
//
//
//    public Employee(String name, String dept, int salary) {
//        this.name = name;
//        this.dept = dept;
//        this.salary = salary;
//    }
//
//
//
//    @Override
//    public String toString() {
//        return "Employee{" + "name='" + name + '\'' +
//                ", dept='" + dept + '\'' + ", salary=" + salary +
//        '}';
//    }
//}