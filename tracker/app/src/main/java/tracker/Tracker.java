package tracker;

import java.util.Scanner;

public class Tracker {

    private StudentService service;

    public void startTracker(Scanner scanner) {
        service = new StudentService();

        System.out.println("Learning Progress Tracker");

        String cmd = null;
        while (true) {
            cmd = scanner.nextLine().trim();
            switch (cmd) {
                case Command.EMPTY:
                    System.out.println("No input.");
                    break;

                case Command.ADD:
                    addStudents(scanner);
                    break;

                case Command.BACK:
                    System.out.println("Enter 'exit' to exit the program");
                    break;

                case Command.LIST:
                    service.listStudents();
                    break;

                case Command.ADD_POINTS:
                    addPoints(scanner);
                    break;

                case Command.FIND:
                    findStudent(scanner);
                    break;

                case Command.EXIT:
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Error: unknown command!");
                    break;
            }
        }
    }

    private void addStudents(Scanner scanner) {
        System.out.println("Enter student credentials or 'back' to return:");

        int studentCount = 0;

        String input = null;
        while (true) {
            input = scanner.nextLine().trim();

            if (Command.BACK.equals(input)) {
                System.out.printf("Total %d students have been added.\n", studentCount);
                return;
            } else {
                if (service.addStudent(input)) {
                    studentCount++;
                }
            }
        }
    }

    private void addPoints(Scanner scanner) {
        System.out.println("Enter an id and points or 'back' to return:");

        String input = null;
        while (true) {
            input = scanner.nextLine().trim();

            if (Command.BACK.equals(input)) {
                return;
            } else {
                service.addPoints(input);
            }
        }
    }

    private void findStudent(Scanner scanner) {
        System.out.println("Enter an id or 'back' to return:");

        String input = null;
        while (true) {
            input = scanner.nextLine().trim();

            if (Command.BACK.equals(input)) {
                return;
            } else {
                service.findStudent(input);
            }
        }
    }
}
