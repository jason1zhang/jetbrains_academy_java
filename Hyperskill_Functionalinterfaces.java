import java.util.Scanner;
import java.util.function.*;

/**
 * 
 * This problem is from Jetbrains Academy topic - Functional interfaces
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-04-28
 *
 * Problem 1 statement:
 *   A lambda expression with seven arguments
 *
 *     Write a lambda expression that accepts seven (!) string arguments and returns a string in uppercase concatenated from all of them (in the order of arguments).
 *       Solution format. Submit your lambda expression with seven arguments in any valid format with ; at the end.
 *       Examples (only with two args): (x, y) -> x + y; (x, y) -> { return x + y; };
 * 
 * Problem 2 statement:
 *   Built-in classes
 *
 *     In which cases can we replace an anonymous class with a lambda expression? All interfaces are from the standard java class library.
 */

class Hyperskill_Functionalinterfaces {

    public static void main(String[] args) {

        // A lambda expression with seven arguments
        Scanner scanner = new Scanner(System.in);
        String[] arrofStr = scanner.nextLine().split(" ");

        SeptenaryStringFunction fun = (s1, s2, s3, s4, s5, s6, s7) -> (s1 + s2 + s3 + s4 + s5 + s6 + s7).toUpperCase();

        System.out.print(fun.apply(arrofStr[0], arrofStr[1], arrofStr[2], arrofStr[3], arrofStr[4], arrofStr[5], arrofStr[6]));

        scanner.close();

        // Built-in classes
        // Answer: choice b and d
        
        // choice a: 
        // Note, the Serializable is a standard marker interface that doesn't contain any methods.
        /*
         * The Serializable interface is present in java.io package. It is a marker interface. 
         * A Marker Interface does not have any methods and fields. Thus classes implementing it do not have to implement any methods. 
         * 
         * Classes implement it if they want their instances to be Serialized or Deserialized. 
         * Serialization is a mechanism of converting the state of an object into a byte stream. 
         * 
         * Serialization is done using ObjectOutputStream. 
         * Deserialization is the reverse process where the byte stream is used to recreate the actual Java object in memory. 
         * 
         * This mechanism is used to persist the object. Deserialization is done using ObjectInputStream. 
         * Thus it can be used to make an eligible for saving its state into a file.
         */
        final double variable = 10;
        Serializable serializable = new Serializable() {
            double applyFun(double x) {
                return x + variable;
            }
        };

        // choice b:
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(100);
                return "hello";
            }
        };
        
        // choice c:
        Future<Double> future = new Future<Double>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }
                    
            @Override
            public boolean isDone() {
                return false;
            }
        
            @Override
            public Double get() throws InterruptedException, ExecutionException {
                return null;
            }
        
            // and other methods...
        };
        
        // choice d:
        // see NIO.2
        PathMatcher matcher = new PathMatcher() {
            @Override
            public boolean matches(Path path) {
                return false;
            }
        };                
      
    }
}

@FunctionalInterface
interface SeptenaryStringFunction {
    String apply(String s1, String s2, String s3, String s4, String s5, String s6, String s7);
}