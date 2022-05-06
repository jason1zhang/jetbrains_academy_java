import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.*;

/**
 * 
 * This problem is from Jetbrains Academy topic - Stream filtering
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-06
 *
 * Problem statement:
 *  Omitting long strings
 *      Implement the omitLongStrings method that takes a list of strings and returns a stream 
 *      that consists of the elements from a given list that are less than 4 characters long.
 *
 *      Example: ["a", "bbb", "cccc", "dddddd"] → ["a", "bbb"]
*/

public class Hyperskill_Stream_Filter {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str = reader.readLine();
        List<String> list = new ArrayList<>(Arrays.asList(str.split(" ")));
        omitLongStrings(list).forEach(System.out::println);
    }

    private static Stream<String> omitLongStrings(List<String> strings) {
        // write your code here
        return strings.stream().filter(str -> str.length() < 4);
    }    
}