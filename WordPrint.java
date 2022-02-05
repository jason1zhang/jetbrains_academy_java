import java.util.Scanner;

class WordPrint{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // start coding here
        String word;
        while (scanner.hasNextLine()) {
            word = scanner.next();
            System.out.println(word);
        }
    }
}