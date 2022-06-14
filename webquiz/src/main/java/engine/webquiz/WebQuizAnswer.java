package engine.webquiz;

public class WebQuizAnswer {
    private static final String CORRECT_ANSWER = "Congratulations, you're right!";
    private static final String WRONG_ANSWER = "Wrong answer! Please, try again.!";
    private boolean success;
    
    public WebQuizAnswer(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getFeedback() {
        return success ? CORRECT_ANSWER : WRONG_ANSWER;
    }
}
