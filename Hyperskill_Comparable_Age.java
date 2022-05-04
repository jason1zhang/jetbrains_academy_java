import java.util.*;
import java.util.stream.Collectors;

/**
 * 
 * This problem is from Jetbrains Academy topic - Comparable
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-04
 *
 * Problem statement:
*   Value class:
*
*       A personal data processing system uses class Age instead of a primitive value to store a person's age. 
*       Change this class so that make it possible to compare to Age objects.
*/

public class Hyperskill_Comparable_Age {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Age> list = Arrays.stream(sc.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .mapToObj(Age::new)
                .sorted()
                .collect(Collectors.toList());

        // Checker.check(list);
        
        System.out.println(list);

        sc.close();
    }
}

class Age implements Comparable<Age> {
    private final int value;

    public Age(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(Age age) {
        return Integer.compare(this.value, age.getValue());
    }

    @Override
    public String toString() {
        return String.format("Age {value = %d}", this.value);
    }
}