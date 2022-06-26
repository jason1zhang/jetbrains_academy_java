package engine.webquiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CompletedQuizService {

    @Autowired
    private CompletedQuizRepository completedQuizRep;


    public Page<CompletedQuiz> getPagedQuizzes(Integer pageNo, Integer pageSize, User user) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("completedAt").descending());

        return completedQuizRep.findAllByUser(user, paging);
    }
}
