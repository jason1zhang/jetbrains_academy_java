import java.util.stream.*;
import java.util.stream.Collectors;
import java.io.*;
import java.util.*;

/**
 * 
 * This problem is from Jetbrains Academy topic - Reduction methods
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-07
 *
 * Problem statement:
 *  Range quadratic sum
 *      Implement the provided method rangeQuadraticSum that takes range borders (fromIncl is inclusive, toExcl is exclusive) 
 *      and calculates the sum of the squares of the elements which belong to the range.
 *
 *      Please, use streams to solve the problem.
 *
 *      Sample Input 1:
 *          10 12
 *      Sample Output 1:
 *          221
 *
 *      Sample Input 2:
 *          5 6
 *      Sample Output 2:
 *          25
 *
 *      Sample Input 3:
 *          3 3
 *      Sample Output 3:
 *          0
*/

class Hyperskill_Reduction_QuadraticSum {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] list = scanner.nextLine().split("\\s+");

        System.out.println(rangeQuadraticSum_2(Integer.parseInt(list[0]), Integer.parseInt(list[1])));

        scanner.close();
    }

    // my implemetation
    public static long rangeQuadraticSum_1(int fromIncl, int toExcl) {
        OptionalInt optionalInt = IntStream.range(fromIncl, toExcl)
                                            .map(n -> n * n)
                                            .reduce(Integer::sum);

        return optionalInt.isPresent() ? optionalInt.getAsInt() : 0;
    }

    // other's implemetation
    public static long rangeQuadraticSum_2(int fromIncl, int toExcl) {
        return LongStream.range(fromIncl, toExcl)
                .reduce(0, (sum, num) -> sum += num * num);
    }

}