import java.util.Scanner;

/**
 * The cinema has n rows, each row consists of m seats (n and m do not exceed 20). A two-dimensional
 * matrix stores the information on the sold tickets: the number 1 means that the ticket for this
 * place is already sold, and the number 0 means that the place is available. You want to buy k
 * tickets to neighboring seats in the same row. Find whether it can be done.
 *
 * Input data format
 *
 * On the input, the program gets the number of n rows and m seats. Then, there are n lines, each
 * containing m numbers (0 or 1) separated by spaces. The last line contains the number k.
 *
 * Output data format
 *
 * The program should output the number of the row with k consecutive available seats. If there
 * are several rows with k available seats, output the first row with these seats. If there is no
 * such a row, output the number 0.
 *
 *
 * Sample Input 1:
 * 3 4
 * 0 1 0 1
 * 1 1 0 1
 * 1 0 0 1
 * 2
 * 
 * Sample Output 1:
 * 3
 * 
 * 
 * Sample Input 2:
 * 3 3
 * 0 1 0
 * 1 0 0
 * 1 1 1
 * 3
 * 
 * Sample Output 2:
 * 0
 */

class CinemaSeats {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();  // n rows
        int m = scanner.nextInt();  // m seats on each row
        int[][] seats = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                seats[i][j] = scanner.nextInt();
            }
        }

        int k = scanner.nextInt();  // k tickets to neighboring seats in the same row
        int count = 0;  // the number of available seats in the same row
        boolean found = false;  // the flag of find the desired row

        for (int i = 0; i < n && !found; i++) {
            for (int j = 0; j < m; j++) {
                if (seats[i][j] == 0) {
                    count++;
                    if (count == k) {
                        System.out.println(i + 1);  // print out the row number desired
                        found = true;
                        break;
                    }
                } else {
                    count = 0;  // reset the count
                }
            }

            count = 0;  // reset the count
        }

        if (!found) {
            System.out.println(0);
        }
    }
}