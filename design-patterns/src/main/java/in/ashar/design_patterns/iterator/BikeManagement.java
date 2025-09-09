package in.ashar.design_patterns.iterator;

import java.util.ArrayList;
import java.util.List;

public class BikeManagement {

    private final List<Bike> bikes = new ArrayList<>();

    public void addBike(String bikeName, int topSpeed){
        bikes.add(new Bike(bikeName,topSpeed));
    }

    public void removeBike(Bike bike){
        bikes.remove(bike);
    }

    public void removeBike(int index){
        bikes.remove(index);
    }

    public MyIterator getIterator(){
        return new IterateList<>(bikes);
    }
}
