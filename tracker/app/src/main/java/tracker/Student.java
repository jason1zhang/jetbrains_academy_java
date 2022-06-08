package tracker;

public class Student {
    private String firstName;
    private String lastName;
    private String email;

    public Student() {
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

    public boolean setEmail(String email) {
        if (!validateEmail(email)) {
            return false;
        }

        this.email = email;
        return true;
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

    private boolean validateEmail(String email) {
        String pattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-]+$";
        return email.matches(pattern);
    }

    private boolean validateName(String name) {
        String pattern = "[a-zA-Z](['-]?[a-zA-Z]+)+";
        return name.matches(pattern);
    }
}
