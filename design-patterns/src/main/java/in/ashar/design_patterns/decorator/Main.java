package in.ashar.design_patterns.decorator;

public class Main {

    public static void main(String[] args) {

        Engineer traineeEngineer = new TraineeEngineer();
        System.out.println("Trainee Engineer");
        System.out.println("Skills : "+traineeEngineer.skills());
        System.out.println("PayScale : "+traineeEngineer.payScale());
        System.out.println();

        Engineer softwareEngineer = new SoftwareEngineer(traineeEngineer);
        System.out.println("Software Engineer");
        System.out.println("Skills : "+softwareEngineer.skills());
        System.out.println("PayScale : "+softwareEngineer.payScale());
        System.out.println();

        Engineer CarEngineArchitect = new MechanicalEngineer(softwareEngineer);
        System.out.println("Car Engine Architect");
        System.out.println("Skills : "+CarEngineArchitect.skills());
        System.out.println("PayScale : "+CarEngineArchitect.payScale());
        System.out.println();
    }

}
