package in.ashar.design_patterns.decorator;


public class SoftwareEngineer implements Engineer {

    private final Engineer engineer;

    public SoftwareEngineer(Engineer engineer) {
        this.engineer = engineer;
    }

    public String skills(){
        return engineer.skills() + "coding, ";
    }

    public int payScale(){
        return engineer.payScale() + 350000;
    }

}
