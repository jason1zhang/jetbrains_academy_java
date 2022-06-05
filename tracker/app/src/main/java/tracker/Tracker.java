package tracker;

import java.util.Scanner;

public class Tracker {

    public void startTracker(Scanner scanner) {
        System.out.println(Message.TITLE);

        String cmd = null;

        while (true) {

            cmd = scanner.nextLine().trim();
            switch (cmd) {
                case "":
                    System.out.println(Message.NO_INPUT);
                    break;

                case "add students":
                    addStudent(scanner);
                    break;

                case "back":
                    System.out.println(Message.BACK);
                    break;

                case "exit":
                    System.out.println(Message.EXIT);
                    return;
                default:
                    System.out.println(Message.UNKNOWN);
                    break;
            }
        }
    }

    private void addStudent(Scanner scanner) {
        int studentCount = 0;

        System.out.println(Message.ADD_STUDENTS);

        String input = null;

        while (true) {
            input = scanner.nextLine().trim();

            if (input.equals("back")) {
                System.out.printf("Total %d students have been added.\n", studentCount);
                return;
            }

            Student student = new Student(input);

            if (!student.isValidInfo()) {
                System.out.println(Message.INCORRECT_CREDENTIALS);
            } else {
                if (!student.validateFirstName()) {
                    System.out.println(Message.INCORRECT_FIRSTNAME);
                } else if (!student.validateLastName()) {
                    System.out.println(Message.INCORRECT_LASTNAME);
                } else if (!student.validateEmail()) {
                    System.out.println(Message.INCORRECT_EMAIL);
                } else {
                    System.out.println(Message.STUDENT_ADDED);
                    studentCount++;
                }
            }
        }
    }
}
