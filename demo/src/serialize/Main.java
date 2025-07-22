package serialize;


import java.io.*;

class Person implements Serializable
{

    transient String name;
    int id;


    public Person(String name, int id) {
        this.name = name;
        this.id = id;
    }

    private void writeObject(ObjectOutputStream os) throws IOException {
        os.defaultWriteObject();
        os.writeObject(this.name);

    }

    private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
        is.defaultReadObject();
        this.name = (String) is.readObject();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}


public class Main {

    public static void main(String[] args) {
        Person person=new Person("Mahesh",22);
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("C:\\Users\\jalas\\OneDrive\\Desktop\\person3.ser"));) {
            objectOutputStream.writeObject(person);
            System.out.println("Created");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try(ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("C:\\Users\\jalas\\OneDrive\\Desktop\\person3.ser")))
        {
            Person person1=(Person) objectInputStream.readObject();
            System.out.println(person1);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
