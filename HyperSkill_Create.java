import java.util.Scanner;

/**
 * 
 * This problem is from Jetbrains Academy Knowledge verification - Anonymous classes
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-04-26
 *
 * Problem statement: Implement Runnable
 *      Java has a standard interface named java.lang.Runnable with the single method run. The method has no arguments and returns nothing.
 *      
 *      You should implement the given method createRunnable that takes two arguments: text and repeats. 
 *      The method must return an instance of an anonymous class implementing java.lang.Runnable. 
 *      The overridden method of the anonymous class should print the text to the standard output a specified number of times (repeats).
 *
 *      Use the provided code template, do not write the standard interface java.lang.Runnable.
 *      For example, if the text is "Hello!" and repeats is 3, the output should be:
 *
 *      Hello!
 *      Hello!
 *      Hello!
 */

public class HyperSkill_Create {

    public static void main(String[] args) {

        HyperSkill_Create.createRunnable("Hello!", 3).run();
    }

    public static Runnable createRunnable(String text, int repeats) {
        return new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < repeats; i++) {
                    System.out.println(text);
                }
            }
        };
    }
}
