import java.util.*;
/**
 * 
 * This problem is from Jetbrains Academy topic - Hiding and overriding
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-03
 *
 */

 public class Hyperskill_Overriding {

    public static void main(String[] args) {
        A a = new D();
        a.method();
    }
}


class A {

    public void method() {
        System.out.println("A");
    }
}

class B extends A {

    public void method() {
        System.out.println("B");
    }
}

class C extends B {

}

class D extends C {

    public void method() {
        super.method();
    }
}