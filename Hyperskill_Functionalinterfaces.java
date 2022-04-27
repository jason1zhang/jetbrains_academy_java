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
 * Problem statement:
 *   A lambda expression with seven arguments
 *
 *     Write a lambda expression that accepts seven (!) string arguments and returns a string in uppercase concatenated from all of them (in the order of arguments).
 *       Solution format. Submit your lambda expression with seven arguments in any valid format with ; at the end.
 *       Examples (only with two args): (x, y) -> x + y; (x, y) -> { return x + y; };
 */

class Hyperskill_Functionalinterfaces {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] arrofStr = scanner.nextLine().split(" ");

        SeptenaryStringFunction fun = (s1, s2, s3, s4, s5, s6, s7) -> (s1 + s2 + s3 + s4 + s5 + s6 + s7).toUpperCase();

        System.out.print(fun.apply(arrofStr[0], arrofStr[1], arrofStr[2], arrofStr[3], arrofStr[4], arrofStr[5], arrofStr[6]));

        scanner.close();
    }
}

@FunctionalInterface
interface SeptenaryStringFunction {
    String apply(String s1, String s2, String s3, String s4, String s5, String s6, String s7);
}