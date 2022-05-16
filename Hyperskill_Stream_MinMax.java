import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * 
 * This problem is from Jetbrains Academy topic - Functional data processing with streams
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-06
 *
 * Problem statement:
*   Finding max and min elements
*/

class Hyperskill_Stream_MinMax {

    public static <T> void findMinMax(
            Stream<? extends T> stream,
            Comparator<? super T> order,
            BiConsumer<Optional<T>, Optional<T>> minMaxConsumer) {

        // your implementation here
        List<T> list = stream.collect(Collectors.toList());

        if (list.isEmpty()) {
            minMaxConsumer.accept(null, null);
        } else {
            minMaxConsumer.accept(list.stream().min(order), list.stream().max(order));
        }
    }
}