package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import model.Question;
public class Exam implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id; // Unique identifier for the exam
    private String student;
    private List<Question> questions;
    private List<String> answers;
    private int score;
    private LocalDateTime dateTime; // Time of the exam
    public Exam(String student, List<Question> questions, List<String> answers) {
        this.id = UUID.randomUUID().toString(); // Generate unique identifier
        this.student = student;
        this.questions = questions;
        this.answers = answers;
        this.dateTime = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getStudent() {
        return student;
    }

    public LocalDateTime getTime() {
        return dateTime.truncatedTo(ChronoUnit.SECONDS);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getSubject() {
        if (questions != null && !questions.isEmpty()) {
            return questions.get(0).getSubject();
        }
        return null;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int calculateScore() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String studentAnswer = answers.get(i);
            if (question.checkAnswer(studentAnswer)) {
                score++;
            }
        }
        this.score = score;
        return score;
    }

    public void recalculateScore() {
        this.score = calculateScore();
    }
}
