package engine.webquiz;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class WebQuizService {
    
    private final WebQuizRepository quizRepository;

    public WebQuizService() {
        quizRepository = new WebQuizRepository();
    }

    public void addWebQuiz(WebQuiz quiz) {
        this.quizRepository.addWebQuiz(quiz);;
    }

    public List<WebQuiz> getAllQuizes() {
        return this.quizRepository.getAllQuizes();
    }

    public WebQuiz getQuizById(int id) {
        return this.quizRepository.getQuizById(id);
    }    
}
