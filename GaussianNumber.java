import java.util.Random;
import java.util.Scanner;

/**
 * For the given numbers K, N, and M find the first seed that is greater than or equal to K 
 * where each of N Gaussian numbers is less than or equal to M.
 *
 * The input contains numbers K, N, M in a single line.
 *
 * You should output the seed.
 *
 * You have to check all N iterations of Gaussian generated numbers to be less than or equal 
 * to M. If that is true, then escape loop and print the seed. If any of the generated numbers 
 * is greater than M, then you have to test out the next seed and go to the loop all over again.
 *
 *
 * Sample Input 1:
 * 0 5 0
 * 
 * Sample Output 1:
 * 38
 * 
 * 
 * Sample Input 2:
 * 0 5 -1.5
 * 
 * Sample Output 2:
 * 498666
 * 
 * 
 * Sample Input 3:
 * 10000 1 1.9
 * 
 * Sample Output 3:
 * 10000
 */
public class GaussianNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // write your code here
        int k = scanner.nextInt();
        int n = scanner.nextInt();
        double m = scanner.nextDouble();

        int seed = k;   // initialize the seed
        Random rand = new Random(seed); // initialize the Random object

        double randNum;

        while (true) {
            int i = 0;  // initialize the loop variable each time

            for (; i < n; i++) {
                randNum = rand.nextGaussian();  // generate the Gaussian number

                // the generated random number not meet the requirement, and break out of the for loop
                if (randNum > m) {
                    break;
                }
            }

            if (i == n) {   // found the seed, and break out of the while loop
                break;
            } else {
                seed++;             // increase the seed
                rand.setSeed(seed); // reset the seed
            }
        }

        System.out.println(seed);
    }
}