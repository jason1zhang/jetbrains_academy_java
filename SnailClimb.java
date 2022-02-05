import java.util.Scanner;

class SnailClimb{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        int height = scanner.nextInt();
        int up = scanner.nextInt();
        int down = scanner.nextInt();

        int distance = 0;
        int days = 1;
        while (distance + up < height) {
            distance = distance + up - down;
            days++;
        }

        System.out.println(days);
    }
}