package tracker;

public class Student {
    private String info;
    private String firstName;
    private String lastName;
    private String email;
    private boolean validInfo;

    public Student(String info) {
        this.info = info;
        this.validInfo = true;

        extractInfo();
    }

    private void extractInfo() {
        String[] infoArray = this.info.split(" ");

        int len = infoArray.length;
        if (len < 3) {
            this.validInfo = false;
            return;
        }

        this.firstName = infoArray[0];

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < len - 1; i++) {
            sb.append(infoArray[i]).append(" ");
        }
        this.lastName = sb.toString();

        this.email = infoArray[len - 1];
    }

    public boolean validateFirstName() {
        return validateName(this.firstName);
    }

    public boolean validateLastName() {
        for (String str : this.lastName.split(" ")) {
            if (!validateName(str)) {
                return false;
            }
        }

        return true;
    }

    public boolean validateEmail() {
        String pattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-]+$";
        return this.email.matches(pattern);
    }

    private boolean validateName(String name) {
        String pattern = "[a-zA-Z](['-]?[a-zA-Z]+)+";
        return name.matches(pattern);
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public boolean isValidInfo() {
        return this.validInfo;
    }
}
