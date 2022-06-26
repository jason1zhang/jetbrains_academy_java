package engine.webquiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void deleteQuizById(int id) {
        this.quizRepository.delete(getQuizById(id));
    }

    public Page<WebQuiz> getPagedQuizzes(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id"));

        return quizRepository.findAll(paging);
    }
}