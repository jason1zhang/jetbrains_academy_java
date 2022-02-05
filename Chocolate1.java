import java.util.Scanner;

/**
 * Problem: Chocolate
 *
 * Imagine a chocolate bar. Remember how it is usually split into smaller bits by a special grid?
 *
 * Now think of chocolate as an N x M rectangle divided into little segments: N segments in length and M in width.
 * Each segment is 1x1 and unbreakable. Find out whether it is possible to break off exactly K segments from the \
 * chocolate with a single straight line: vertical or horizontal.
 *
 * Input data format
 *
 * The program gets an input of three integers: N, M, K.
 *
 * Output data format
 *
 * The program must output one of the two words: YES or NO.
 *
 * If you're having trouble understanding the task, try drawing it on a piece of paper.
 * 
 * Sample Input 1:
 * 4
 * 2
 * 6
 * Sample Output 1:
 * YES
 * 
 * 
 * Sample Input 2:
 * 2
 * 10
 * 7
 * Sample Output 2:
 * NO
 * 
 * 
 * Sample Input 3:
 * 7
 * 4
 * 21
 * Sample Output 3:
 * YES
 */

class Chocolate1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // start coding here
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int k = scanner.nextInt();

        if (isBreakable(n, m, k)) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    public static boolean isBreakable(int n, int m, int k) {
        boolean flag = false;

        if (k > n * m) {
            flag = false;
        } else if (k == n * m) {
            flag = true;
        } else {
            for (int i = 1; i < m; i++) {
                if (k == i * n) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                for (int i = 1; i < n; i++) {
                    if (k == i * m) {
                        flag = true;
                        break;
                    }
                }
            }
        }

        return flag;
    }
}