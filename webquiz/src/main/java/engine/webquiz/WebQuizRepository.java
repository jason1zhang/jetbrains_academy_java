package engine.webquiz;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface WebQuizRepository extends PagingAndSortingRepository<WebQuiz, Integer> {

}
