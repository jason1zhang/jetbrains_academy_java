import java.util.Scanner;

/**
 * 
 * This problem is from Jetbrains Academy Knowledge verification - Anonymous classes
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-04-26
 *
 * Problem statement:
*   There is an interface Returner:
*
*       interface Returner {
*           public String returnString();
*           public int returnInt();
*       }
*
*   You should create an anonymous class that implements the interface and assign the instance to the variable returner. 
*   The anonymous class must override both methods of the interface. The method returnString should capture the string variable str from the context and return it, 
*   the second method should capture the integer variable number from the context and return it. These variables will be accessible during testing.
 */

public class HyperSkill_Returner {

    public static void main(String[] args) {

        final Scanner scanner = new Scanner(System.in);
        final String str = scanner.nextLine();
        final int number = Integer.parseInt(scanner.nextLine());

        Returner returner = new Returner() {
            @Override
            public String returnString() {
                return str;
            }

            @Override
            public int returnInt() {
                return number;
            }

        };

        System.out.println(returner.returnString());
        System.out.println(returner.returnInt()); 

        scanner.close();
    }
}

interface Returner {

    public String returnString();

    public int returnInt();
}