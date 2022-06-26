package engine.webquiz;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Completed_Quiz")
public class CompletedQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "completed_quiz_Id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int quizID;

    @Column(name = "id")
    private int id;

    @Column(name = "completedAt")
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    public CompletedQuiz() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
