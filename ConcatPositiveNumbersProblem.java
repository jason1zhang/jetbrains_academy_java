import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Concat positive numbers of two ArrayLists
 * 
 * solve the practice problem from the "ArrayList" topic in hyperskill
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-04-05
 */

class ConcatPositiveNumbersProblem {

    /**
     * Implement a method to concatenate all positive numbers of two input ArrayLists to a single resulting list.
     * At first, elements from the first ArrayList should go, and then the elements from the second ArrayList.
     */
    public static ArrayList<Integer> concatPositiveNumbers(ArrayList<Integer> l1, ArrayList<Integer> l2) {
        int i = 0;
        while (i < l1.size()) {
            if (l1.get(i) <= 0) {
                l1.remove(i);
            } else {
                i++;
            }
        }

        for (i = 0; i < l2.size(); i++) {
            if (l2.get(i) > 0) {
                l1.add(l2.get(i));
            }
        }

        return l1; // write your code here
    }

    /* Do not modify this method */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Integer> list1 = readArrayList(scanner);
        ArrayList<Integer> list2 = readArrayList(scanner);

        ArrayList<Integer> result = concatPositiveNumbers(list1, list2);

        result.forEach(n -> System.out.print(n + " "));
    }

    /* Do not modify this method */
    private static ArrayList<Integer> readArrayList(Scanner scanner) {
        return Arrays
                .stream(scanner.nextLine().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}