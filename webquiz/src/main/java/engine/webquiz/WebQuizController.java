package engine.webquiz;

import java.util.List;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebQuizController {
    @Autowired
    private WebQuizService service;

    @PostMapping("/api/quizzes")
    public WebQuiz addWebQuiz(@RequestBody WebQuiz quiz) {
        service.addWebQuiz(quiz);
        return quiz;
    }

    @GetMapping("api/quizzes")
    public List<WebQuiz> getAllQuizes() {
        return service.getAllQuizes();
    }

    @GetMapping("api/quizzes/{id}")
    public ResponseEntity<WebQuiz> getQuizById(@PathVariable int id) {
        WebQuiz quiz = this.service.getQuizById(id);
        if (quiz != null) {
            return new ResponseEntity<>(this.service.getQuizById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<?> getFeedback(@PathVariable int id, @RequestParam(required = true) int answer) {
        WebQuiz quiz = this.service.getQuizById(id);

        if (quiz == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            int correctAnswer = quiz.getAnswer();
            return ResponseEntity.ok(new WebQuizAnswer(answer == correctAnswer));
        }
    }
}
