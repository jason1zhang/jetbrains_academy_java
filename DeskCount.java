import java.util.Scanner;

class DeskCount{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        int desks = 0;
        int students = 0;
        while (scanner.hasNextLine()) {
            students = scanner.nextInt();
            desks += (students / 2 + students % 2);
        }

        System.out.println(desks);
    }
}