package view;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controller.ExamController;
import controller.QuestionController;
import view.AnswerQuestionsView;

import javax.script.ScriptEngineManager;

public class StudentView extends VBox {
    private String username;
    private QuestionController questionController;
    private static ExamController examController = new ExamController();
    public StudentView(Stage primaryStage, String username, QuestionController questionController) {
        this.username = username;
        this.questionController = questionController;
        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(10);
        // Answer Questions Button
        Button answerQuestionsButton = new Button("选择科目并答题");
        answerQuestionsButton.setOnAction(e -> primaryStage.setScene(new Scene(new view.AnswerQuestionsView(primaryStage, username, questionController, examController), 600, 400)));
        // View Scores Button
        Button viewScoresButton = new Button("查看成绩");
        viewScoresButton.setOnAction(e -> primaryStage.setScene(new Scene(new view.ViewScoresView(primaryStage, username, examController), 600, 400)));
        // Add buttons to layout
        getChildren().addAll(answerQuestionsButton, viewScoresButton);
    }
}
