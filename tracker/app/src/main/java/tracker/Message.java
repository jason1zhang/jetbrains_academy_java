package tracker;

public enum Message {
    TITLE ("Learning Progress Tracker"),
    BACK ("Enter 'exit' to exit the program."),
    EXIT ("Bye!"),
    NO_INPUT ("No input."),
    UNKNOWN ("Error: unknown command!"),

    ADD_STUDENTS ("Enter student credentials or 'back' to return:"),
    STUDENT_ADDED ("The student has been added."),
    INCORRECT_CREDENTIALS ("Incorrect credentials."),
    INCORRECT_FIRSTNAME ("Incorrect first name."),
    INCORRECT_LASTNAME ("Incorrect last name."),
    INCORRECT_EMAIL ("Incorrect email.");    

    public final String msg;

    private Message (String msg) {
        this.msg = msg;
    }

    public String toString() {
        return this.msg;
    }
}
