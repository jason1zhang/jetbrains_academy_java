import java.util.Scanner;

/**
 * 
 * This problem is from Jetbrains Academy Knowledge verification - Introduction to generic programming
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-04-24
 *
 * Problem statement:
 *  Creating instances 
 *  Imagine a generic class:
 * 
 *      public class Example<T> { large body }
 *  What can be used as X when creating instances?
 *  Example<X> example = ...
 */

public class Demo_Generics {

    public static void main(String[] args) {
        // GenericType<42> gt1 = new GenericType();

        // GenericType<"string"> gt2 = new GenericType();

        GenericType<String> gt3 = new GenericType();

        // GenericType<int> gt4 = new GenericType();

        GenericType<int[]> gt5 = new GenericType();
    }
}

class GenericType <T> {
    private T t;

    public GenericType(T t) {
        this.t = t;
    }

    public T get() {
        return this.t;
    }
}