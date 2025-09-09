package in.ashar.design_patterns.decorator;

public class TraineeEngineer implements Engineer{
    @Override
    public String skills() {
        return "Adaptable, ";
    }

    @Override
    public int payScale() {
        return 100000;
    }
}
