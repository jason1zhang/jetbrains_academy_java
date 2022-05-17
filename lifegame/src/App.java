import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        int size = scanner.nextInt();
        int seed = scanner.nextInt();

        Random random = new Random(seed);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (random.nextBoolean()) {
                    System.out.print("O");
                } else {
                    System.out.print(" ");
                }
            }

            System.out.println("");
        }

        scanner.close();
    }
}
