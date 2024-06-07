package model;
import java.util.Arrays;
import java.util.List;

public class Question {
    private String subject;
    private String type;
    private String questionText;
    private String options;
    private String correctAnswer;
    private List<String> choices;  // 新增属性

    public Question(String subject, String type, String questionText, String options, String correctAnswer) {
        this.subject = subject;
        this.type = type;
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        if (type.equals("choice") && options != null && !options.isEmpty()) {
            this.choices = Arrays.asList(options.split("\\|"));
        }
    }

    // Getters and setters
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
        if (this.type.equals("choice") && options != null && !options.isEmpty()) {
            this.choices = Arrays.asList(options.split(","));
        }
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getChoices() {  // 新增 getter 方法
        return choices;
    }

    public void setChoices(List<String> choices) {  // 新增 setter 方法
        this.choices = choices;
    }

    // Method to check answer
    public boolean checkAnswer(String answer) {
        return this.correctAnswer.equals(answer);
    }
}
