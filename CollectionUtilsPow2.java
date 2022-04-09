import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * solve the practice problem from the "The Collections Framework overview" topic in hyperskill
 * 
 * Implement a method called pow2 that takes a collection of numbers and returns a collection of squares of these numbers.
 * Do not modify elements of the given collection, create a new one and return it as the result instead.
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-04-09
 */

class CollectionUtilsPow2 {

    public static Collection<Integer> pow2(Collection<Integer> numbers) {
        // write your code here
        Collection<Integer> numbersPow2 = new ArrayList<>();

        for (Integer num : numbers) {
            numbersPow2.add((int) Math.pow(num, 2));
        }

        return numbersPow2;
    }
}

/* Please, do not modify this I/O code */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Collection<Integer> numbers = Arrays
                .stream(scanner.nextLine().split("\\s+"))
                .map(Integer::parseInt).collect(Collectors.toList());

        Collection<Integer> result = CollectionUtilsPow2.pow2(numbers);

        System.out.println(result.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" ")));
    }
}