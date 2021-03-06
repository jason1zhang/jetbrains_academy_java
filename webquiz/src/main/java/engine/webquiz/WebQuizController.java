package engine.webquiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

@RestController
@Validated
public class WebQuizController {
    @Autowired
    private WebQuizService service;

    @Autowired
    private CompletedQuizService completedQuizService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private CompletedQuizRepository completedQuizRep;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/actuator/shutdown")
    public ResponseEntity<?> shutDownEngine() {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        String email = user.getEmail();
        if (repository.findByEmail(email) != null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
            repository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @DeleteMapping("api/quizzes/{id}")
    public ResponseEntity<?> deleteQuizById(@PathVariable int id) {
        WebQuiz quiz = this.service.getQuizById(id);

        if (quiz == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else if (!quiz.getCreator().equals(getCurrentUser())) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } else {
            service.deleteQuizById(id);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }

        return null;
    }

    @PostMapping("/api/quizzes")
    public WebQuiz addWebQuiz(@Valid @RequestBody WebQuiz quiz) {
        quiz.setCreator(getCurrentUser());
        service.addWebQuiz(quiz);
        return quiz;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("api/quizzes")
    public ResponseEntity<Page<WebQuiz>> getPagedQuizzes(@RequestParam(defaultValue = "0") Integer page,
                                                         @RequestParam(defaultValue = "10") Integer size) {
        Page<WebQuiz> paging = service.getPagedQuizzes(page, size);
        return new ResponseEntity<>(paging, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("api/quizzes/completed")
    public ResponseEntity<Page<CompletedQuiz>> getPagedCompletedQuizzes(@RequestParam(defaultValue = "0") Integer page,
                                                                        @RequestParam(defaultValue = "10") Integer size) {
        Page<CompletedQuiz> paging = completedQuizService.getPagedQuizzes(page, size, repository.findByEmail(getCurrentUser()));
        return new ResponseEntity<>(paging, new HttpHeaders(), HttpStatus.OK);

    }

    @GetMapping("api/quizzes/{id}")
    public ResponseEntity<WebQuiz> getQuizById(@PathVariable int id) {
        return new ResponseEntity<>(this.service.getQuizById(id), HttpStatus.OK);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<?> getFeedback(@PathVariable int id, @RequestBody(required = true) HashMap<String, ArrayList<Integer>> answer) {
        WebQuiz quiz = this.service.getQuizById(id);

        if (quiz == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            ArrayList<Integer> correctAnswer = quiz.getAnswer();
            ArrayList<Integer> myAnswer = answer.get("answer");

            WebQuizAnswer webQuizAnswer = null;
            if ((correctAnswer == null && (myAnswer == null || myAnswer.size() == 0))
                    || myAnswer.equals(correctAnswer)) {
                webQuizAnswer = new WebQuizAnswer(true);
            } else {
                webQuizAnswer = new WebQuizAnswer(false);
            }

            if (webQuizAnswer.isSuccess()) {
                CompletedQuiz completedQuiz = new CompletedQuiz();
                completedQuiz.setId(id);
                completedQuiz.setCompletedAt(LocalDateTime.now());
                completedQuiz.setUser(repository.findByEmail(getCurrentUser()));
                completedQuizRep.save(completedQuiz);
            }

            return ResponseEntity.ok(webQuizAnswer);
        }
    }
}
