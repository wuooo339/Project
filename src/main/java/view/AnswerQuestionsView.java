package view;


import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Question;
import model.Exam;
import view.StudentView;
import java.util.ArrayList;
import java.util.List;
import controller.ExamController;
import controller.QuestionController;


public class AnswerQuestionsView extends VBox {
    private QuestionController questionController;
    private ExamController examController;
    public AnswerQuestionsView(Stage primaryStage, String username, QuestionController questionController,ExamController examController) {
        this.questionController = questionController;
        this.examController = examController;
        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(10);

        Label subjectLabel = new Label("科目:");
        TextField subjectField = new TextField();
        Button startButton = new Button("开始答题");

        startButton.setOnAction(e -> {
            String subject = subjectField.getText();
            List<Question> questions = questionController.getQuestionsBySubject(subject);
            List<Control> answerFields = new ArrayList<>();
            VBox questionBox = new VBox(10);  // 用于显示所有题目
            questionBox.setPadding(new Insets(10));
            ScrollPane scrollPane = new ScrollPane(questionBox);  // 使用ScrollPane使内容可滚动
            scrollPane.setFitToWidth(true);

            getChildren().clear();
            getChildren().addAll(subjectLabel, subjectField, startButton);

            for (Question question : questions) {
                Label questionLabel = new Label(question.getQuestionText());
                questionBox.getChildren().add(questionLabel);

                if (question.getType().equals("choice")) {
                    ToggleGroup group = new ToggleGroup();
                    List<String> options = question.getChoices();
                    for (String option : options) {
                        RadioButton radioButton = new RadioButton(option);
                        radioButton.setToggleGroup(group);
                        questionBox.getChildren().add(radioButton);
                        answerFields.add(radioButton);
                    }
                } else if (question.getType().equals("blank")) {
                    String[] parts = question.getQuestionText().split("______");
                    for (int i = 0; i < parts.length; i++) {
                        questionBox.getChildren().add(new Label(parts[i]));
                        if (i < parts.length - 1) {
                            TextField textField = new TextField();
                            questionBox.getChildren().add(textField);
                            answerFields.add(textField);
                        }
                    }
                }
            }

            Button submitButton = new Button("提交");
            submitButton.setOnAction(submitEvent -> {
                List<String> answers = new ArrayList<>();
                for (Control answerField : answerFields) {
                    if (answerField instanceof RadioButton) {
                        RadioButton radioButton = (RadioButton) answerField;
                        if (radioButton.isSelected()) {
                            answers.add(radioButton.getText());
                        }
                    } else if (answerField instanceof TextField) {
                        TextField textField = (TextField) answerField;
                        answers.add(textField.getText());
                    }
                }
                Exam exam = examController.createExam(username, questions, answers);
                exam.recalculateScore();
                Label scoreLabel = new Label("测试成绩：" + exam.getScore());
                getChildren().add(scoreLabel);  // 使用正确的方法添加成绩标签
                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished(event -> primaryStage.setScene(new Scene(new StudentView(primaryStage, username, questionController), 600, 400)));
                delay.play();
            });

            questionBox.getChildren().add(submitButton);
            getChildren().add(scrollPane);
        });

        getChildren().addAll(subjectLabel, subjectField, startButton);
    }
}
