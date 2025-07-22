package thisandsuper;

public class ThisAndSuper {

    ThisAndSuper(){
        System.out.println("My no arg Constructor");
    }

    ThisAndSuper(int a){
        this();
        System.out.println("My int arg Constructor : "+a);
    }

    public void inMyMethod(){
        System.out.println("in my method");
    }

    public static void main(String[] args) {
        A objA = new A(1);
        C objC = new C(3);
    }
}

class A {
    A(){
        System.out.println("A's no arg constructor");
    }

    A(int a){
        this();
        System.out.println("A's int arg constructor : "+a);
    }

    public void inA(){
        System.out.println("in A method");
    }
}

class B {
    B(){
        super();
        System.out.println("B's no arg constructor");
    }

    B(int a){
        this();
        System.out.println("B's int arg constructor : "+a);
    }

    public void same(){
        System.out.println("in B method");
    }
}


class C extends B {
    C(){
        super(2);
        System.out.println("C's no arg constructor");
    }

    C(int a){
        this();
        System.out.println("C's int arg constructor : "+a);
    }

    public void same(){
        System.out.println("in C method");
    }
}