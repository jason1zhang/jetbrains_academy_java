package tracker;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    List<Student> students;

    public StudentService() {
        students = new ArrayList<>();
    }

    public boolean addStudent(String studentInfo) {
        Student student = new Student();

        String[] infoArray = studentInfo.split(" ");

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

        if (!student.setEmail(infoArray[len - 1])) {
            System.out.println("Incorrect email.");
            return false;
        }

        students.add(student);

        System.out.println("The student has been added.");
        return true;
    }
}
