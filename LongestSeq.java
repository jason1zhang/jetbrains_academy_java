import java.util.Scanner;

/**
 * Write a program that reads an array of ints and outputs the length of the longest sequence
 * in strictly ascending order. Elements of the sequence must go one after another. A single
 * number is assumed to be an ordered sequence with the length = 1.
 *
 * Input data format
 *
 * The first line contains the size of an array.
 * The second line contains elements of the array separated by spaces.
 *
 * Example
 *
 * The input array is 1 2 4 1 2 3 5 7 4 3. In this case, the length of the longest sequence
 * in ascending order is 5. It includes the following elements: 1 2 3 5 7.
 *
 *
 * Sample Input 1:
 * 10
 * 1 2 4 1 2 3 5 7 4 3
 * 
 * Sample Output 1:
 * 5
 * 
 * 
 * Sample Input 2:
 * 12
 * 1 2 4 1 2 3 5 7 8 9 10 11
 * 
 * Sample Output 2:
 * 9
 */

class LongestSeq {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        int[] seq = new int[k];

        for (int i = 0; i < k; i++) {
            seq[i] = scanner.nextInt();
        }

        int len = 1;
        int maxLen = 1;
        int curr = 0;   // the current index to the array when iterating
        while (curr < k) {
            if ((curr + 1 < k) && (seq[curr + 1] > seq[curr])) {
                len++;
            } else {
                maxLen = Math.max(len, maxLen);
                len = 1;
            }

            curr++;
        }

        System.out.println(maxLen);
    }
}