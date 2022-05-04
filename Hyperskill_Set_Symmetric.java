import java.util.*;
import java.util.stream.Collectors;

/**
 * 
 * This problem is from Jetbrains Academy topic - The Set interface
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-04
 *
 * Problem statement:
*   Symmetric difference:
*
*       The symmetric difference of two sets is the set that contains elements that are in either of the sets but not in their intersection. 
*       In other words, only those elements that are present in one set and not present in the other. Check out the illustration for better understanding.
*
*       Implement a method for finding the symmetric difference of the two given sets of strings. Elements in the resulting set can be in any order.
*
*   Example:
*       The symmetric difference of two sets {1, 2, 3} and {0, 1, 2} is {0, 3}
*/

public class Hyperskill_Set_Symmetric {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String strSet1 = scanner.nextLine();
        String strSet2 = scanner.nextLine();

        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();

        if (!Objects.equals(strSet1, "empty")) {
            Collections.addAll(set1, strSet1.split("\\s+"));
        }

        if (!Objects.equals(strSet2, "empty")) {
            Collections.addAll(set2, strSet2.split("\\s+"));
        }

        Set<String> resultSet = SetUtils.symmetricDifference(set1, set2);

        System.out.println(String.join(" ", resultSet));

        scanner.close();
    }
}

class SetUtils {

    /**
     * @return symmetric difference between set1 and set2
     */
    public static Set<String> symmetricDifference(Set<String> set1, Set<String> set2) {
        Set<String> set1Copy = new HashSet<>(set1);

        set1.removeAll(set2);
        set2.removeAll(set1Copy);
        set1.addAll(set2);

        return set1;
    }
}
