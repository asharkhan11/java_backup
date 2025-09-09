package in.ashar.design_patterns.observer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Setter
/// Subject
public class CaptainAmerica {

    private final List<Avenger> avengers = new ArrayList<>();

    public void addAvenger(Avenger avenger){
        this.avengers.add(avenger);
    }

    public void removeAvenger(Avenger avenger){
        this.avengers.remove(avenger);
    }


    public void avengersAssemble(String messageFromCaptain){
        System.out.println(messageFromCaptain);
        for (Avenger avenger : avengers) {
            avenger.avengerAssemble();
        }
    }

}
