package in.ashar.design_patterns.singleton;

public class SingletonClass {

    private static SingletonClass singletonClass;

    public static SingletonClass getSingletonClass(){
        if(singletonClass == null){
            singletonClass = new SingletonClass();
        }
        return singletonClass;
    }

    private  SingletonClass(){
        System.out.println("singleton object created");
    }


    public static void main(String[] args) {
        SingletonClass singletonClass1 = SingletonClass.getSingletonClass();
        SingletonClass singletonClass2 = SingletonClass.getSingletonClass();

        System.out.println(singletonClass1);
        System.out.println(singletonClass2);
    }

}
