package in.ashar.design_patterns.factory;

public class CountryFactory {

    public static Country getCountry(String name){

        if(name == null) return null;

        return switch (name){
            case "india" -> new India();
            case "america" -> new America();
            default -> () -> System.out.println("Hey Alien !!!");
        };

    }


    public static void main(String[] args) {
        Country country = CountryFactory.getCountry("india");
        country.greet();
    }

}
