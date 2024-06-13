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
import java.util.ArrayList;
import java.util.List;
import controller.ExamController;
import controller.QuestionController;
import controller.UserController;

public class AnswerQuestionsView extends VBox {
    private QuestionController questionController = new QuestionController();
    private ExamController examController;
    private UserController userController;

    public AnswerQuestionsView(Stage primaryStage, String username, ExamController examController, UserController userController) {
        this.examController = examController;
        this.userController = userController;
        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(10);

        Label subjectLabel = new Label("科目:");
        ComboBox<String> subjectComboBox = new ComboBox<>();
        subjectComboBox.getItems().addAll("Math", "Science", "History", "English","Geography","Computer");

        Label difficultyLabel = new Label("难度:");
        ComboBox<Integer> difficultyComboBox = new ComboBox<>();
        difficultyComboBox.getItems().addAll(1, 2, 3);

        Label questionCountLabel = new Label("题目数量:");
        TextField questionCountField = new TextField();

        Button startButton = new Button("开始答题");
        Button logoutButton = new Button("退出");
        logoutButton.setOnAction(e -> {
            primaryStage.setScene(new Scene(new view.StudentView(primaryStage, username, questionController, userController), 600, 400));
        });

        startButton.setOnAction(e -> {
            String subject = subjectComboBox.getValue();
            int difficulty = difficultyComboBox.getValue();
            int questionCount = Integer.parseInt(questionCountField.getText());

            List<Question> questions = questionController.getQuestionsBySubjectAndDifficulty(subject, difficulty, questionCount);
            if (questions.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "没有找到该科目的题目。", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            Exam exam = new Exam(username, questions, new ArrayList<>());
            List<Control> answerFields = new ArrayList<>();
            VBox questionBox = new VBox(10);
            questionBox.setPadding(new Insets(10));
            ScrollPane scrollPane = new ScrollPane(questionBox);
            scrollPane.setFitToWidth(true);

            getChildren().clear();

            for (Question question : questions) {
                Label questionLabel = new Label(question.getQuestionText());
                if (question.getType().equals("choice")) {
                    questionBox.getChildren().add(questionLabel);
                    ToggleGroup group = new ToggleGroup();
                    List<String> options = question.getChoices();
                    for (String option : options) {
                        RadioButton radioButton = new RadioButton(option);
                        radioButton.setToggleGroup(group);
                        questionBox.getChildren().add(radioButton);
                        answerFields.add(radioButton);
                    }
                } else if (question.getType().equals("blank")) {
                    questionBox.getChildren().add(questionLabel);
                    String[] parts = question.getQuestionText().split("______");
                    for (int i = 0; i < parts.length - 1; i++) {
                        TextField textField = new TextField();
                        questionBox.getChildren().add(textField);
                        answerFields.add(textField);
                    }
                }

            }

            Button submitButton = new Button("提交");
            submitButton.setOnAction(submitEvent -> {
                List<String> answers = getStrings(answerFields);
                exam.setAnswers(answers);
                exam.calculateScore();
                examController.addExam(exam);
                Label scoreLabel = new Label("测试成绩：" + exam.getScore());
                getChildren().add(scoreLabel);
                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished(event -> primaryStage.setScene(new Scene(new StudentView(primaryStage, username, questionController, userController), 600, 400)));
                delay.play();
            });
            questionBox.getChildren().add(submitButton);
            getChildren().add(scrollPane);
        });

        getChildren().addAll(subjectLabel, subjectComboBox, difficultyLabel, difficultyComboBox, questionCountLabel, questionCountField, startButton, logoutButton);
    }

    private static List<String> getStrings(List<Control> answerFields) {
        List<String> answers = new ArrayList<>();
        ToggleGroup currentGroup = null;
        boolean added = false;

        for (Control answerField : answerFields) {
            if (answerField instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) answerField;
                if (currentGroup != radioButton.getToggleGroup()) {
                    if (currentGroup != null && !added) {
                        answers.add(""); // If no radio button was selected in the previous group
                    }
                    currentGroup = radioButton.getToggleGroup();
                    added = false;
                }
                if (radioButton.isSelected()) {
                    answers.add(radioButton.getText());
                    added = true;
                }
            } else if (answerField instanceof TextField) {
                TextField textField = (TextField) answerField;
                if (textField.getText().trim().isEmpty()) {
                    answers.add(""); // Set answer to empty if TextField is empty
                } else {
                    answers.add(textField.getText());
                }
            }
        }

        if (currentGroup != null && !added) {
            answers.add(""); // If no radio button was selected in the last group
        }

        return answers;
    }
}
