package in.ashar.design_patterns.builder;

public class Company {

    //Required
    private final int companyId;
    private final String companyName;

    //Optional
    private final int employeeCount;
    private final long revenue;

    private Company(Builder builder){
        this.companyId = builder.companyId;
        this.companyName = builder.companyName;
        this.employeeCount = builder.employeeCount;
        this.revenue = builder.revenue;
    }

    public static class Builder{
        //Required
        private final int companyId;

        //Optional
        private String companyName;
        private int employeeCount=0;
        private long revenue=0;

        //Builder Constructor
        public Builder(int companyId){
            this.companyId = companyId;
        }

        //setter for employee count
        public Builder employeeCount(int employeeCount){
            this.employeeCount = employeeCount;
            return this;
        }

        //setter for company name
        public Builder companyName(String companyName){
            this.companyName = companyName;
            return this;
        }


        //setter for revenue
        public Builder revenue(long revenue){
            this.revenue = revenue;
            return this;
        }


        public Company build(){
            return new Company(this);
        }


    }

    public static void main(String[] args) {
        Company company1 = new Builder(1)
                .companyName("ABC")
                .employeeCount(50)
                .revenue(15900000)
                .build();

        Company company2 = new Builder(2)
                .companyName("PQR")
                .employeeCount(50)
                .revenue(15900000)
                .build();

        System.out.println(company1);
        System.out.println(company2);

    }
}
