package in.ashar.design_patterns.prototype;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Student implements Person{

    private int rollNo;

    public Student(){
        System.out.println("Student object created");
    }


    @Override
    public Person clone() {
        try {
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        Student student1 = new Student();
        Student student2 = (Student) student1.clone();

        System.out.println(student1);
        System.out.println(student2);
    }
}
