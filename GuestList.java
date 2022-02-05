import java.util.Scanner;
import java.util.ArrayList;

class GuestList {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // start coding here
        ArrayList<String> names = new ArrayList<>();
        while (scanner.hasNextLine()) {
            names.add(scanner.next());
        }

        for (int i = names.size() - 1; i >= 0; i--) {
            System.out.println(names.get(i));
        }
    }
}