import java.util.IntSummaryStatistics;
import java.util.stream.IntStream;

/**
 * 
 * This problem is from Jetbrains Academy topic - Streams of primitives
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-08
 *
 * Problem statement:
 *  The default value of summary statistics
 *      Suppose, you have an empty IntStream. What values of IntSummaryStatistics will be equal zero?
 *      You can use your logical thinking or experiment with some code to solve this problem.
*/

public class Hyperskill_Stream_IntStream {

    public static void main(String[] args) {
        IntSummaryStatistics stat = IntStream.empty().summaryStatistics();

        System.out.println(String.format("Count: %d, Min: %d, Max: %d, Avg: %.1f, Sum: %d", 
                                            stat.getCount(), stat.getMin(), stat.getMax(), stat.getAverage(), stat.getSum()));
    }
}