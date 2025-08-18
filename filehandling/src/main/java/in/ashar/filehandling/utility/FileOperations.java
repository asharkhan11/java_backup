package in.ashar.filehandling.utility;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class FileOperations {

    public boolean createDirectory(String directory) {
        File file = new File(directory);
        if (!file.exists()) {
            return file.mkdirs();
        } else {
            System.out.println("directory already exists");
            return false;
        }
    }

    public boolean createFile(String basePath, String fileName) {
        File file = new File(basePath, fileName);

        if (!file.exists()) {
            createDirectory(basePath);
            try {
                return file.createNewFile();
            } catch (IOException e) {
                System.out.println("error : " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("file already exists");
            return false;
        }
    }

    public String getFullPath(File file) {
        if (file.exists()) {
            try {
                return file.getCanonicalPath();
            } catch (IOException e) {
                return "error : " + e.getMessage();
            }
        } else {
            return "file does not exists";
        }
    }

    public String[] listFiles(File file) {
        return file.list();
    }

    public boolean deleteFile(File file) {
        return file.delete();
    }

    public boolean deleteAllFiles(File file) {

        if(file.isDirectory()){
            for(File f : file.listFiles()){
                if(!deleteAllFiles(f)){
                    return false;
                }
            }
        }
        return deleteFile(file);
    }

    public boolean writeContentUsingFileWriter(File file, String content, boolean append){
        try (FileWriter fw = new FileWriter(file, append)) {
            fw.write(content);
            return true;
        } catch (IOException e) {
            System.out.println("error : "+e.getMessage());
            return false;
        }
    }

    public String readContentUsingFileReader(File file){
        StringBuilder sb = new StringBuilder();

        if(file.exists()){
            try (FileReader fr = new FileReader(file)){
                int data = 0;
                while((data = fr.read()) != -1){
                    sb.append((char) data);
                }
            } catch (IOException e) {
                return "error : "+e.getMessage();
            }
        }else {
            return "file not exists";
        }

        return sb.toString();
    }


    public boolean writeContentUsingBufferWriter(File file, String content, boolean append){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file,append))){
            bw.write(content);
        }catch (IOException e){
            System.out.println("error : "+e.getMessage());
            return false;
        }
        return true;
    }

    public String readContentUsingBufferReader(File file){

        StringBuilder sb = new StringBuilder();

        if(file.exists()){
            try (BufferedReader br = new BufferedReader(new FileReader(file))){
                Stream<String> lines = br.lines();
                lines.forEach(line->{
                    sb.append(line).append(System.lineSeparator());
                });
            } catch (IOException e) {
                return "error : "+e.getMessage();
            }
        }
        else {
            return "file not exists";
        }

        return sb.toString();
    }

    public boolean writeContentUsingFileOutputStream(File file, String content, boolean append){
        try (FileOutputStream fos = new FileOutputStream(file,append)){

            fos.write(content.getBytes());
            return true;
        }catch (IOException e){
            System.out.println("error : "+e.getMessage());
            return false;
        }
    }

    public String readContentUsingFileInputStream(File file){

        if(file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {

                byte[] bytes = fis.readAllBytes();
                return new String(bytes, StandardCharsets.UTF_8);

            } catch (IOException e) {
                return "error : " + e.getMessage();
            }
        }else {
            return "file not exists";
        }
    }

}
