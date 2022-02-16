import java.util.Scanner;

/**
 * Recursion print strings in the reverse order
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-02-16
 *  
 * Given a recursive method which should print an input string in the reverse order.
 */

public class ReverseString {

    public static void printReverseCharByChar(String s) {
        if (s.length() > 0) {
            int last = s.length() - 1;
            System.out.print(s.charAt(last));
            printReverseCharByChar(s.substring(0, last));
        }
    }

    /* Do not change code below */
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        printReverseCharByChar(scanner.nextLine());
    }
}