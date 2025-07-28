package custom.exceptions;

import java.util.ArrayList;
import java.util.List;

public class User {
    List<StudentEntity> users;

    User(){

        users = new ArrayList<>();
        users.add(new StudentEntity(1,"abc",10));
        users.add(new StudentEntity(2,"lmn",12));
        users.add(new StudentEntity(3,"pqr",15));
        users.add(new StudentEntity(4,"stu",20));
        users.add(new StudentEntity(5,"xyz",15));
    }


    public StudentEntity getUserByName(String name) throws MyException{

        for(StudentEntity s : users){
            if(s.getName().equals(name)){
                return s;
            }
        }

        throw new MyException("User Not Found.");
    }

}
