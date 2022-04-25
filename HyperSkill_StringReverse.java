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
 *  There is an interface StringReverser:
 *      interface StringReverser {
 *          String reverse(String str);
 *      }
 * 
 * You should create an anonymous class that implements the interface and assign the instance to the variable reverser. 
 * The anonymous class must override the method reverse of the interface. It should return the reversed input string.
 */

class HyperSkill_StringReverse {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        StringReverser reverser = new StringReverser() {
            @Override
            public String reverse(String str) {
                StringBuilder sb = new StringBuilder();

                for (int i = str.length() - 1; i >= 0; i--) {
                    sb.append(str.charAt(i));
                }

                return sb.toString();
            }
        };

        System.out.println(reverser.reverse(line));

        scanner.close();
    }

    interface StringReverser {

        public String reverse(String str);
    }

}