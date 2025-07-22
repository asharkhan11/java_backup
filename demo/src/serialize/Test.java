package serialize;

import java.io.*;

class Mohit implements Serializable {
    int legs;
    transient int hands;

    public Mohit(int legs, int hands) {
        this.legs = legs;
        this.hands = hands;
    }

    public int getLegs() {
        return legs;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public int getHands() {
        return hands;
    }

    public void setHands(int hands) {
        this.hands = hands;
    }

    @Override
    public String toString() {
        return "Mohit{" +
                "legs=" + legs +
                ", hands=" + hands +
                '}';
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.hands = (int) in.readObject();
    }

    private void writeObject(ObjectOutputStream os) throws IOException {
        os.defaultWriteObject();
        os.writeObject(this.hands);
    }


}

public class Test {

    public static void main(String[] args) {
        Mohit m = new Mohit(2, 2);
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("C:\\Users\\jalas\\OneDrive\\Desktop\\mohit.ser"));
            os.writeObject(m);

            ObjectInputStream is = new ObjectInputStream(new FileInputStream("C:\\Users\\jalas\\OneDrive\\Desktop\\mohit.ser"));
            Mohit mohit = (Mohit) is.readObject();
            System.out.println(mohit);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
