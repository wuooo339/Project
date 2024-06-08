package view;
import controller.QuestionController;
import controller.ExamController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Exam;
import java.util.List;

public class ViewScoresView extends VBox {
    private ExamController examController;
    private QuestionController questionController;
    public ViewScoresView(Stage primaryStage, String username, ExamController examController) {
        this.examController = examController;
        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(10);
        List<Exam> exams = examController.getExamsByStudent(username);
        if (exams.isEmpty()) {
            Label noScoresLabel = new Label("没有成绩记录。");
            getChildren().add(noScoresLabel);
        } else {
            for (Exam exam : exams) {
                Label scoreLabel = new Label("科目: " + exam.getSubject() + " 成绩: " + exam.getScore());
                getChildren().add(scoreLabel);
            }
        }
        Button backButton = new Button("返回");
        backButton.setOnAction(e -> primaryStage.setScene(new Scene(new view.StudentView(primaryStage, username, questionController), 600, 400)));
        getChildren().add(backButton);
    }
}
