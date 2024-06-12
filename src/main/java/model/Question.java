package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Question implements Serializable {
    private static final long serialVersionUID = 1L;  // 添加一个唯一的序列化版本ID
    private String id;
    private String subject;
    private String type;
    private String questionText;
    private String options;
    private String correctAnswer;
    private transient List<String> choices;  // 使用 transient 关键字忽略此字段的序列化

    public Question(String subject, String type, String questionText, String options, String correctAnswer) {
        this.id = UUID.randomUUID().toString();
        this.subject = subject;
        this.type = type;
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        if (type.equals("choice")) {
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
        if (this.type.equals("choice")) {
            this.choices = Arrays.asList(options.split("\\|"));
        }
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getChoices() {
        if (choices == null && type.equals("choice")) {
            this.choices = Arrays.asList(options.split("\\|"));
        }
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    // Method to check answer
    public boolean checkAnswer(String answer) {
        return this.correctAnswer.equals(answer);
    }

    // 序列化后重建 choices 字段
    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (type.equals("choice")) {
            this.choices = Arrays.asList(options.split("\\|"));
        }
    }

    public String getId() {
        return this.id;
    }

    public String toString() {
        return "Subject: " + subject + ", Type: " + type + ", Question: " + questionText;
    }

}
