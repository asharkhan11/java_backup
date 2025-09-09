package in.ashar.design_patterns.observer;

public class Main {

    public static void main(String[] args) {

        /// Subscribers
        Avenger thor = new Thor();
        Avenger ironMan = new IronMan();
        Avenger hulk = new Hulk();

        /// Subject
        CaptainAmerica captainAmerica = new CaptainAmerica();
        captainAmerica.addAvenger(thor);
        captainAmerica.addAvenger(ironMan);
        captainAmerica.addAvenger(hulk);

        /// Notify all
        captainAmerica.avengersAssemble("Avengers Assemble !!!");

    }
}
