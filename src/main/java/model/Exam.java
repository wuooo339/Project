package model;

import java.util.List;

public class Exam {
    private String student;
    private List<Question> questions;
    private List<String> answers;
    private int score;

    public Exam(String student, List<Question> questions, List<String> answers) {
        this.student = student;
        this.questions = questions;
        this.answers = answers;
        this.score = calculateScore();
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
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

    private int calculateScore() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String studentAnswer = answers.get(i);
            if (question.checkAnswer(studentAnswer)) {
                score++;
            }
        }
        return score;
    }

    public void recalculateScore() {
        this.score = calculateScore();
    }
}
