package engine.webquiz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class WebQuizRepository {
    private List<WebQuiz> quizList;
    private int count;

    public WebQuizRepository() {
        this.quizList = new ArrayList<>();
        this.count = 0;
    }

    public void addWebQuiz(WebQuiz quiz) {
        quiz.setId(++this.count);
        this.quizList.add(quiz);
    }

    public List<WebQuiz> getAllQuizes() {
        return this.quizList;
    }

    public WebQuiz getQuizById(int id) {
        // return id <= quizList.size() && id >= 1 ? this.quizList.get(id - 1) : null;

        if (id <= quizList.size() && id >= 1) {
            return this.quizList.stream()
                                .filter(quiz -> quiz.getId() == id)
                                .findAny()
                                .get();
        } else {
            return null;
        }
    }

    
}
