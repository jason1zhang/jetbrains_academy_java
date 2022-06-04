import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Learning Progress Tracker");

        String cmd = null;
        while (true) {

            cmd = scanner.nextLine();

            if (cmd == null || cmd.trim().length() == 0) {
                System.out.println("No input.");
            } else if (cmd.equals("exit")) {
                System.out.println("Bye!");
                break;
            } else {
                System.out.println("Error: unknown command!");
            }
        }

        scanner.close();
    }
}
