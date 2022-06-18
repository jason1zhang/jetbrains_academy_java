package engine.webquiz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebQuizService {
    
    @Autowired
    private WebQuizRepository quizRepository;

    public void addWebQuiz(WebQuiz quiz) {
        this.quizRepository.save(quiz);;
    }

    public List<WebQuiz> getAllQuizes() {
        return (List<WebQuiz>) this.quizRepository.findAll();
    }

    public WebQuiz getQuizById(int id) {
        return this.quizRepository.findById(id).get();
    }    
}
