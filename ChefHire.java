import java.util.Scanner;

class ChefHire{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // start coding here
        String firstName = scanner.nextLine();
        String yearOfExperience = scanner.nextLine();
        String cuisinePref = scanner.nextLine();
        String output = String.format("The form for %s is completed. " +
                "We will contact you if we need a chef who cooks %s dishes " +
                "and has %s years of experience.", firstName, cuisinePref, yearOfExperience);

        System.out.println(output);
    }
}