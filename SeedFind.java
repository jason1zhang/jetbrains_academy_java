import java.util.*;

/**
 * Your job is to find the seed between A and B (both inclusive) that produces N pseudorandom numbers
 * from 0 (inclusive) to K (exclusive). It should also have the maximum of these N numbers to be the
 * minimum among all maximums of other seeds in this range.
 *
 * Sounds complicated? Take a look at the example.
 *
 * Here we have A = 7, B = 9, N = 4, K = 100. Let's suppose:
 *
 * For the seed 7, we get the sequence 45, 99, 23, 67 – the maximum is 99.
 * For the seed 8, we get 64, 34, 23, 9 – the maximum is 64.
 * For the seed 9, we get 78, 34, 0, 11 – the maximum is 78.
 * Then the minimum among these maximums is 64. That means, in this example, the seed we are looking for is 8.
 *
 * The input contains numbers A, B, N, K in a single line and in this order.
 *
 * Your task is to output 2 numbers: a seed and its maximum. If there are some seeds with equal minimal
 * maximums, you should output the seed that is smaller than all other seeds.
 *
 *
 * Sample Input 1:
 * 0 100 5 1000
 * 
 * Sample Output 1:
 * 18
 * 270
 * 
 * 
 * Sample Input 2:
 * 0 100 1000 1000
 * 
 * Sample Output 2:
 * 5
 * 993
 * 
 * Sample Input 3:
 * 0 100 10000 1000
 * 
 * Sample Output 3:
 * 0
 * 999
 */

public class SeedFind {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // write your code here
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int n = scanner.nextInt();
        int k = scanner.nextInt();

        Random rand = new Random();
        int randNum;
        int minSeed = a;        // initialize the seed
        int minMaxRandNum = k;  // initialize the minimum of maximum number for all runs

        for (int seed = a; seed <= b; seed++) {
            rand.setSeed(seed); // set the seed

            int maxRandNum = 0; // maximum random number for each run
            for (int i = 0; i < n; i++) {
                randNum = rand.nextInt(k);
                maxRandNum = Math.max(randNum, maxRandNum);
            }

            // get the required seed and the minimum of maximum number for all runs
            if (minMaxRandNum > maxRandNum) {
                minMaxRandNum = maxRandNum;
                minSeed = seed;
            }
        }

        System.out.println(minSeed);
        System.out.println(minMaxRandNum);
    }
}