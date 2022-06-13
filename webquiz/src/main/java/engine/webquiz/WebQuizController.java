package engine.webquiz;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebQuizController {
    @Autowired
    private WebQuizService service;

    @GetMapping("/api/quiz")
    public WebQuiz getWebQuiz() {
        return this.service.getQuiz();
    }

    @PostMapping("/api/quiz")
    public ResponseEntity<?> getFeedback(@RequestParam(required = true) int answer) {
        LinkedHashMap<String, Object> status = new LinkedHashMap<>();
        if (answer == 2) {
            status.put("success", true);
            status.put("feedback", "Congratulations, you're right!");
        } else {
            status.put("success", false);
            status.put("feedback", "Wrong answer! Please, try again.");
        }
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
