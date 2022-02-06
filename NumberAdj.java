import java.util.Scanner;

/**
 * Write a program that reads an array of integers and two numbers n and m. The program
 * must check that n and m never occur next to each other (in any order) in the array.
 *
 * Input data format
 *
 * The first line contains the size of an array.
 *
 * The second line contains elements of the array.
 *
 * The third line contains two integer numbers n and m.
 *
 * All numbers in the same line are separated by the space character.
 *
 * Output data format
 *
 * The result is a single boolean value true if n and m never occur next to each other;
 * otherwise, it is false.
 *
 *
 * Sample Input 1:
 * 3
 * 1 2 3
 * 2 3
 * 
 * Sample Output 1:
 * false
 * 
 * 
 * Sample Input 2:
 * 3
 * 1 2 3
 * 3 4
 * 
 * Sample Output 2:
 * true
 * 
 * 
 * Sample Input 3:
 * 10
 * 3 4 5 6 3 4 6 4 4 8
 * 5 8
 * 
 * Sample Output 3:
 * true
 */

class NumberAdj {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        int[] seq = new int[k];

        for (int i = 0; i < k; i++) {
            seq[i] = scanner.nextInt();
        }

        int n, m;
        n = scanner.nextInt();
        m = scanner.nextInt();

        int prev = seq[0];
        int curr;
        boolean isAdj = false;  // is next to each other?
        for (int i = 1; i < k; i++) {
            curr = seq[i];
            if ((prev == m && curr == n) || (prev == n && curr == m)) {
                isAdj = true;
                break;
            } else {
                prev = curr;
            }
        }

        System.out.println(!isAdj); // to meet the requirement, need to negate the boolean variable isAdj
    }
}