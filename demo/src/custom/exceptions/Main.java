package custom.exceptions;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        User u = new User();
        try {

            StudentEntity student = u.getUserByName("Ashar");
            System.out.println("student id : " + student.getId());
            System.out.println("student name : " + student.getName());
            System.out.println("student age : " + student.getAge());

        }
        catch (MyException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Inside Finally Block");
        }


    }
}
