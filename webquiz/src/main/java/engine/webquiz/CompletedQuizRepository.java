package engine.webquiz;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

@Repository
public interface CompletedQuizRepository extends PagingAndSortingRepository<CompletedQuiz, Integer>{

    Page<CompletedQuiz> findAllByUser(User user, Pageable pageable);
}
