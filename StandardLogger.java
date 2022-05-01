import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.*;

/**
 * 
 * This program is from Jetbrains Academy topic - Standard logger
 * 
 * Problem: Find integer values of Log levels
 *  You will be given a list of space-separated log levels. Find the total integer value of the given log level set.
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-01
 *
 */

public class StandardLogger {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        // String input = scanner.nextLine();
        // String[] levels = input.split(" ");

        // System.out.println(findSum(levels));

        int sum = 0;
        while (scanner.hasNext()) {
            String level = scanner.next().toUpperCase();
            sum += Level.parse(level).intValue();
        }
        
        System.out.println(sum);
        
        scanner.close();
    }

    /**
     * get the values from the input logging levels
     * 
     * @param levels a string array of logging levels
     * @return the total integer value of the given log level set.
     */
    private static int findSum(String[] levels) {
        HashMap<String, Integer> map = new HashMap<>();

        map.put("severe", 1000);
        map.put("warning", 900);
        map.put("info", 800);
        map.put("config", 700);
        map.put("fine", 500);
        map.put("finer", 400);
        map.put("finest", 300);

        int sum = 0;
        for (String level : levels) {
            sum += map.get(level);
        }

        return sum;
    }
}