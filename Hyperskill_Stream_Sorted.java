import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * 
 * This problem is from Jetbrains Academy topic - Functional data processing with streams
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-06
 *
 * Problem statement:
*   A sorted stream
*/

public class Hyperskill_Stream_Sorted {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        sortAndPrint(Arrays.stream(scanner.nextLine().split("\\s+")));

        scanner.close();
    }

    /**
     * Prints sorted elements of a given stream of strings.
     *
     * @param wordStream the input stream of strings
     */
    public static void sortAndPrint(Stream<String> wordStream) {
        // write your code here
        wordStream.sorted().forEach(System.out::println);
    }
}