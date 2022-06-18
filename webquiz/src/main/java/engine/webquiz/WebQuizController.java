package engine.webquiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class WebQuizController {
    @Autowired
    private WebQuizService service;

    @PostMapping("/api/quizzes")
    public WebQuiz addWebQuiz(@Valid @RequestBody WebQuiz quiz) {
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
    public List<WebQuiz> getAllQuizes() {
        return service.getAllQuizes();
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

            if (correctAnswer == null && (myAnswer == null || myAnswer.size() == 0))
                return ResponseEntity.ok(new WebQuizAnswer(true));

            return ResponseEntity.ok(new WebQuizAnswer(myAnswer.equals(correctAnswer)));
        }
    }
}
