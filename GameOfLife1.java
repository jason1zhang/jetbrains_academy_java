import java.util.*;

/**
 * The 1sth development stage of the Jetbrains Academy project "Game of Life"
 *
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-18 
 * 
 */

public class GameOfLife1 {
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