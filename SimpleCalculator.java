import java.util.Scanner;

/**
 * Write a simple calculator that reads the three values from the line: the first number, the operation,
 * and the second number.
 *
 * The program should apply the operation to the numbers entered ("first number" "operation" "second number")
 * and output the result to the screen. Note that the numbers are long.
 *
 * The calculator should support:
 *
 * addition: "+"
 * subtraction: "-"
 * integer division: "/"
 * multiplication: "*"
 * If a user performs division and the second number is 0, it is necessary to output the line "Division by 0!".
 *
 * If the input operator is not one from the list, the program should output "Unknown operator".
 *
 * Note: it's recommended to use the switch statement in your solution.
 *
 * Sample Input 1:
 * 10000000000 + 20000000000
 * Sample Output 1:
 * 30000000000
 *
 *
 * Sample Input 2:
 * 3000 / 0
 * Sample Output 2:
 * Division by 0!
 *
 *
 * Sample Input 3:
 * 10000 ! 300
 * Sample Output 3:
 * Unknown operator
 */
class SimpleCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // start coding here
        long num1 = scanner.nextLong();
        String oper = scanner.next();
        long num2 = scanner.nextLong();

        switch (oper) {
            case "+":
                System.out.println(num1 + num2);
                break;
            case "-":
                System.out.println(num1 - num2);
                break;
            case "/":
                if (num2 == 0) {
                    System.out.println("Division by 0!");
                } else {
                    System.out.println(num1 / num2);
                }

                break;
            case "*":
                System.out.println(num1 * num2);
                break;
            default:
                System.out.println("Unknown operator");
                break;
        }
    }
}