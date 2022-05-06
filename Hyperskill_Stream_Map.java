import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 
 * This problem is from Jetbrains Academy topic - Map and flatMap
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-06
 *
 * Problem statement:
 *  Sorting the absolute values
 *      Write a code that returns an array of sorted integer absolute numbers of the given array. 
 *      The elements should be sorted in ascending order.
 *
 *      Sample Input 1:
 *          1 2 6 -3 -9
 *      Sample Output 1:
 *          1 2 3 6 9
*/

public class Hyperskill_Stream_Map {

    /**
     * Returns the sorted array of absolute numbers in ascending order.
     *
     * @param numbers the input array of String integer numbers
     * @return the sorted array of integer absolute numbers
     */
    public static int[] sortedAbsNumbers(String[] numbers) {
        // write your code here
        return Arrays.stream(numbers)
                .map(number -> Math.abs(Integer.parseInt(number)))
                .sorted()
                .mapToInt(Integer::intValue)    // Convert Integer List to Integer Array
                .toArray();
    }

    // Don't change the code below
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(Arrays.stream(sortedAbsNumbers(scanner.nextLine().split("\\s+")))
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" "))
        );

        scanner.close();
    }
}