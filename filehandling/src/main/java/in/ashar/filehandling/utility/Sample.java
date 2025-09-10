package in.ashar.filehandling.utility;

import java.io.File;
import java.io.IOException;

public class Sample {


    public static void main(String[] args) {

        File file = new File("file1.txt");


        try {
            boolean newFile = file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
