import java.util.Scanner;

/**
 * Check the string is palindrome
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-02-16
 */

public class PalindromeString {

    public static boolean isPalindrome(String s) {
        if (s.length() == 0 || s.length() == 1) { // (1)
            return true; // (2)
        }
            
        int lastIndex = s.length() - 1; // (3)
        boolean r = s.charAt(0) == s.charAt(lastIndex); // (4)
         
        return r && isPalindrome(s.substring(1, lastIndex)); // (5)
    }

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        
        if (isPalindrome(str)) {
            System.out.printf("String {%s} is a palindrome.\n", str);
        } else {
            System.out.printf("String {%s} is NOT a palindrome.\n", str);            
        }

        scanner.close();
    }
}