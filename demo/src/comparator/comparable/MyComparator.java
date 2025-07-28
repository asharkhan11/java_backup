package comparator.comparable;

import java.util.Comparator;

class MyComparator implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {
        if(o1.getId()- o2.getId() < 0){
            return 1;
        }
        else if(o1.getId()- o2.getId() > 0){
            return -1;
        }
        else {
            return 0;
        }
    }
}