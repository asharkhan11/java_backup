package in.ashar.design_patterns.decorator;


public class MechanicalEngineer implements Engineer{

    private final Engineer engineer;

    public MechanicalEngineer(Engineer engineer) {
        this.engineer = engineer;
    }

    @Override
    public String skills() {
        return  engineer.skills() +"engine design, ";
    }

    @Override
    public int payScale() {
        return engineer.payScale() + 500000;
    }
}
