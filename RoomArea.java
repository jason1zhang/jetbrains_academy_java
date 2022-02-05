import java.util.Scanner;

/**
 * Citizens of the country named Malevia often experiment with the shapes of their rooms.
 * The rooms can be triangular, rectangular, and round.
 *
 * Write a program that calculates the floor area of the rooms.
 * Input data format: The type of the room shape and the relevant parameters.
 * Output data format: The area of the resulting room.
 *
 * Note that the value of 3.14 is used instead of the number π in Malevia.
 *
 * Input format used by the Malevians:
 *
 * triangle
 * a
 * b
 * c
 * where a, b, and c are lengths of the triangle sides.
 *
 * rectangle
 * a
 * b
 * where a and b are lengths of the rectangle sides.
 *
 * circle
 * r
 * where r is the radius of the circle.
 *
 * Note that the input values (a, b, c, r) are doubles, and your answer should be too.
 *
 * 
 * Sample Input 1:
 * rectangle
 * 4
 * 10
 * 
 * Sample Output 1:
 * 40.0
 * 
 * 
 * Sample Input 2:
 * circle
 * 5
 * 
 * Sample Output 2:
 * 78.5
 * 
 * 
 * Sample Input 3:
 * triangle
 * 3
 * 3
 * 3
 * 
 * Sample Output 3:
 * 3.897114317029974
 */

class RoomArea {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // start coding here
        final double pi = 3.14;
        String roomType = scanner.next();
        int a, b, c, r;
        double area = 0.0;
        switch (roomType) {
            case "triangle":
                a = scanner.nextInt();
                b = scanner.nextInt();
                c = scanner.nextInt();
                double p = (a + b + c) * 0.5; // half of parameter of triangle
                area = Math.sqrt(p * (p - a) * (p - b) * (p - c));
                break;
            case "rectangle":
                a = scanner.nextInt();
                b = scanner.nextInt();
                area = a * b;
                break;
            case "circle":
                r = scanner.nextInt();
                area = pi * r * r;
                break;
            default:
                System.out.println("Wrong room type!");
                break;
        }

        System.out.println(area);
    }
}