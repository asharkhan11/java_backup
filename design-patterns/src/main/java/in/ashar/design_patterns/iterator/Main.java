package in.ashar.design_patterns.iterator;


public class Main {


    public static void main(String[] args) {

        BikeManagement garage = new BikeManagement();

        garage.addBike("Hero",100);
        garage.addBike("Suzuki",120);
        garage.addBike("Kawasaki",270);
        garage.addBike("Himalaya",200);

        MyIterator iterator = garage.getIterator();

        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

    }


}
