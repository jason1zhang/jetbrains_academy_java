package engine.webquiz;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface WebQuizRepository extends CrudRepository<WebQuiz, Integer> {
  
}
