import java.util.function.UnaryOperator;

/**
 * 
 * This problem is from Jetbrains Academy topic - Lambda expressions
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-04-27
 *
 * Problem statement:
*   Creating a closure:
*
*      Write a lambda expression that takes single String argument, removes all whitespaces on its both ends and adds PREFIX (before) and SUFFIX (after) to it. 
*      PREFIX and SUFFIX are final variables provided in the code template.
 */

class Hyperskill_PrefixSuffixOperator {

    public static void main(String[] args) {

        final String PREFIX = "__pref__";
        final String SUFFIX = "__suff__";

        /*
         * The UnaryOperator Interface<T> is a part of the java.util.function package which has been introduced since Java 8, 
         * to implement functional programming in Java. It represents a function which takes in one argument and operates on it. 
         * However what distinguishes it from a normal Function is that both its argument and return type are the same.
        */
        UnaryOperator<String> operator = str -> {
            return PREFIX + str.trim() + SUFFIX;
        };

        System.out.println(operator.apply("   hello world!   "));
    }
}