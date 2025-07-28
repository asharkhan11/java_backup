package comparator.comparable;

import java.util.*;

public class Demo {

    public static void main(String[] args){

        comparator.comparable.MyComparator ascAge = new comparator.comparable.MyComparator();
        Comparator<comparator.comparable.Student> descAge = (a,b)->{
          if(a.getAge() > b.getAge()) return -1;

          else if(a.getAge() < b.getAge()) return 1;

          else {
              return b.getName().compareToIgnoreCase(a.getName());
          }
        };

        ArrayList<comparator.comparable.Student> l = new ArrayList<>();
        comparator.comparable.Student st1 = new comparator.comparable.Student(2,"Ashar",22);
        comparator.comparable.Student st2 = new comparator.comparable.Student(3,"Bilal",20);
        comparator.comparable.Student st3 = new comparator.comparable.Student(1,"Rumaan",21);
        comparator.comparable.Student st4 = new comparator.comparable.Student(4,"Aayush",22);

        l.add(st1);
        l.add(st2);
        l.add(st3);
        l.add(st4);

//        List<comparator.comparable.Student> list = l.stream().sorted(getByAccendingAge).toList();
        l.sort(ascAge);
        System.out.println("comparator :" +l);


        Collections.sort(l);
        System.out.println("comparable :" +l);

        l.sort(descAge);
        System.out.println("comparator :" +l);



    }
}



