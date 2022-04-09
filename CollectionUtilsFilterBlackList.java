import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Using a blacklist to filter outthe phone numbers.
 * 
 * solve the practice problem from the "The Collections Framework overview" topic in hyperskill
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-04-09
 */

class CollectionUtilsFilterBlackList {

    public static Collection<String> filterPhones(Collection<String> phones,
                                                  Collection<String> blacklist) {
        // write your code here
        phones.removeIf(blacklist::contains);

        return phones;
    }
}

/* Please, do not modify this I/O code */
class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Collection<String> phones = Arrays.asList(scanner.nextLine().split("\\s+"));
        Collection<String> blockList = Arrays.asList(scanner.nextLine().split("\\s+"));

        Collection<String> nonBlockedPhones = CollectionUtilsFilterBlackList.filterPhones(
                new ArrayList<>(phones), new ArrayList<>(blockList));

        System.out.println(nonBlockedPhones.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" ")));

        scanner.close();
    }
}