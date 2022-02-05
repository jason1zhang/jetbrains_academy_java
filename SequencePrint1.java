import java.util.Scanner;

/**
 * Write a program that prints a part of the sequence 1 2 2 3 3 3 4 4 4 4 5 5 5 5 5 ... 
 * (the number is repeated as many times, to what it equals to). The input to the program 
 * is a positive integer n: the number of the elements of the sequence the program should 
 * print. Output the sequence of numbers, written in a single line, space-separated.
 *
 * For example, if n = 7, then the program should output 1 2 2 3 3 3 4.
 */

class SequencePrint1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // start coding here
        int k = scanner.nextInt();
        int step = 1;
        int accuSteps = 0;
        while (accuSteps < k) {
            int i = 0;
            for (; (i < step) && (accuSteps + i < k); i++) {
                System.out.print(step + " ");
            }

            accuSteps += step;
            step++;
        }
    }
}