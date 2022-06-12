package tracker;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private final List<Student> students;
    private int studentID;

    public StudentService() {
        this.students = new ArrayList<>();
        this.studentID = 10000;
    }

    public boolean addStudent(String input) {
        Student student = new Student();

        String[] infoArray = input.split(" ");

        int len = infoArray.length;
        if (len < 3) {
            System.out.println("Incorrect credentials.");
            return false;
        }

        if (!student.setFirstName(infoArray[0])) {
            System.out.println("Incorrect first name.");
            return false;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < len - 1; i++) {
            sb.append(infoArray[i]).append(" ");
        }

        if (!student.setLastName(sb.toString())) {
            System.out.println("Incorrect last name.");
            return false;
        }

        if (!student.validateEmail(infoArray[len - 1])) {
            System.out.println("Incorrect email.");
            return false;
        } else {
            if (this.students
                    .stream()
                    .anyMatch((s) -> s.getEmail().equals(infoArray[len - 1]))) {
                System.out.println("This email is already taken.");
                return false;
            }

            student.setEmail(infoArray[len - 1]);
        }

        student.setId(Integer.toString(studentID));
        studentID++;

        this.students.add(student);

        System.out.println("The student has been added.");
        return true;
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public void listStudents() {
        if (this.students.size() == 0) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("Students:");
        this.students
                .forEach((student) -> System.out.println(student.getId()));
    }

    public void addPoints(String input) {
        String[] infoArray = input.split(" ");

        if (infoArray.length != 5) {
            System.out.println("Incorrect points format.");
            return;
        }

        String id = infoArray[0];

        if (this.students
                .stream()
                .noneMatch((s) -> s.getId().equals(id))) {
            System.out.println("No student is found for id=" + id);
            return;
        }

        for (String str : infoArray) {
            if (!str.matches("[0-9]+")) {
                System.out.println("Incorrect points format.");
                return;
            }
        }

        Student student = this.students
                .stream()
                .filter((s) -> s.getId().equals(id))
                .findFirst()
                .get();

        student.setPoints(Integer.parseInt(infoArray[1]),
                Integer.parseInt(infoArray[2]),
                Integer.parseInt(infoArray[3]),
                Integer.parseInt(infoArray[4]));

        System.out.println("Points updated.");
    }

    public void findStudent(String input) {
        if (this.students
                .stream()
                .noneMatch((s) -> s.getId().equals(input))) {
            System.out.println("No student is found for id=" + input);
            return;
        }

        System.out.println(this.students
                .stream()
                .filter((s) -> s.getId().equals(input))
                .findFirst()
                .get());
    }

    public void notifyStudent() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        
        for (Student student : this.students) {
            String msg = student.notifyMessage();
            if (msg.length() > 0) {
                count++;
                sb.append(msg);
            }
        }

        System.out.print(sb.toString());
        System.out.printf("Total %d students have been notified.\n", count);
    }
}
