package engine.webquiz;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class WebQuizService {
    private WebQuiz quiz;

    public WebQuizService() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Robot");
        options.add("Tea leaf");
        options.add("Cup of coffee");
        options.add("Bug");

        quiz = new WebQuiz("The Java Logo", "What is depicted on the Java logo?", options);
    }

    public WebQuiz getQuiz() {
        return this.quiz;
    }
    
}
