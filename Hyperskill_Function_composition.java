import java.util.function.*;

/**
 * 
 * This problem is from Jetbrains Academy topic - Function composition
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-04
 *
 * Problem statement:
*   Composing consumers:
*/

public class Hyperskill_Function_composition {

    public static void main(String[] args) {
        Consumer<Integer> printer = System.out::println;
        Consumer<Integer> devNull = (val) -> { int v = val * 2; };  // Remember that Consumer doesn't return anything.
        
        Consumer<Integer> combinedConsumer = devNull.andThen(devNull.andThen(printer));
        combinedConsumer.accept(100);        
    }
}