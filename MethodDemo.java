import java.util.Scanner;

/**
 * This program is to demonstrate the usage of various methods in the java
 * system classes.
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-02-13
 */
public class MethodDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String strMethod;
        boolean isDone = false;

        do {
            System.out.print("Enter the method to demonstrate: > ");
            strMethod = scanner.next();

            switch (strMethod) {
                case "nextLine":
                    nextLineDemo(scanner);
                    break;
                case "done":
                    isDone = true;
                    break;
                default:
                    System.out.println("Unexpected method name, please enter again!\n");
                    break;
            }
        } while (!isDone);

        scanner.close();
    }

    /**
     * This method is to demonstrate the difference between the method next() and
     * nextLine().
     * 
     * @param scanner java.util.Scanner
     */
    private static void nextLineDemo(Scanner scanner) {
        System.out.printf(
                "\n*** This is to demo the usage of method \"nextLine()\" in the class java.util.Scanner. ***\n");

        System.out.print("next(): > ");
        String s1 = scanner.next();
        System.out.printf("s1 -> (%s)\n\n", s1);

        System.out.print("nextLine(): > ");
        scanner.nextLine(); // Important! Here need to call nextLine() one extra line to get rid of the carriage return
        String s2 = scanner.nextLine();
        System.out.printf("s2 -> (%s)\n\n", s2);
    }
}