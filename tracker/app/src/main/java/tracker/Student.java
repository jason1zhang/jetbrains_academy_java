package tracker;

import java.util.ArrayList;

public class Student {
    private String firstName;
    private String lastName;
    private String email;
    private String id;
    private final ArrayList<Course> courses;

    public Student() {
        courses = new ArrayList<>();

        Course courseJava = new Course("Java", 600);
        Course courseDSA = new Course("DSA", 400);
        Course courseDB = new Course("Databases", 480);
        Course courseSpring = new Course("Spring", 550);

        courses.add(courseJava);
        courses.add(courseDSA);
        courses.add(courseDB);
        courses.add(courseSpring);
    }

    public String getFirstName() {
        return this.firstName;
    }

    public boolean setFirstName(String firstName) {
        if (!validateFirstName(firstName)) {
            return false;
        }

        this.firstName = firstName;
        return true;
    }

    public String getLastName() {
        return this.lastName;
    }

    public boolean setLastName(String lastName) {
        if (!validateLastName(lastName)) {
            return false;
        }

        this.lastName = lastName;
        return true;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Course> getCourses() {
        return this.courses;
    }

    public void setPoints(int pointJava, int pointDSA, int pointDB, int pointSpring) {
        this.courses.get(0).setPoint(this.courses.get(0).getPoint() + pointJava);
        this.courses.get(1).setPoint(this.courses.get(1).getPoint() + pointDSA);
        this.courses.get(2).setPoint(this.courses.get(2).getPoint() + pointDB);
        this.courses.get(3).setPoint(this.courses.get(3).getPoint() + pointSpring);

        this.courses.forEach(course -> {
            if (course.getPoint() == course.getTotalPoint()) {
                course.setCompleted(true);
            }
        });

        this.courses.get(0).setActivity(pointJava == 0 ? this.courses.get(0).getActivity() : this.courses.get(0).getActivity() + 1);
        this.courses.get(1).setActivity(pointDSA == 0 ? this.courses.get(1).getActivity() : this.courses.get(1).getActivity() + 1);
        this.courses.get(2).setActivity(pointDB == 0 ? this.courses.get(2).getActivity() : this.courses.get(2).getActivity() + 1);
        this.courses.get(3).setActivity(pointSpring == 0 ? this.courses.get(3).getActivity() : this.courses.get(3).getActivity() + 1);
    }

    @Override
    public String toString() {
        return this.id + " points: " + "Java=" + this.courses.get(0).getPoint()
                + "; DSA=" + this.courses.get(1).getPoint()
                + "; Databases=" + this.courses.get(2).getPoint()
                + "; Spring=" + this.courses.get(3).getPoint();
    }

    private boolean validateFirstName(String firstName) {
        return validateName(firstName);
    }

    private boolean validateLastName(String lastName) {
        for (String str : lastName.split(" ")) {
            if (!validateName(str)) {
                return false;
            }
        }

        return true;
    }

    public boolean validateEmail(String email) {
        String pattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-]+$";
        return email.matches(pattern);
    }

    private boolean validateName(String name) {
        String pattern = "[a-zA-Z](['-]?[a-zA-Z]+)+";
        return name.matches(pattern);
    }

    public String notifyMessage() {
        StringBuilder sb = new StringBuilder();

        for (Course course : this.courses) {
            if (course.isCompleted() && !course.isNotified()) {
                sb.append(String.format("To: %s\nRe: Your Learning Progress\nHello, %S %s! You have accomplished our %S course!\n", 
                            this.email, this.firstName, this.lastName, course.getName()));
                
                course.setNotified(true);
            }
        }

        return sb.toString();
    }
}
