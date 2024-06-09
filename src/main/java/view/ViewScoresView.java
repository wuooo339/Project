package view;
import controller.QuestionController;
import controller.ExamController;
import controller.UserController;
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
    private UserController userController;
    public ViewScoresView(Stage primaryStage, String username, ExamController examController, UserController userController) {
        this.examController = examController;
        this.userController = userController;
        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(10);
        List<Exam> exams = examController.getExamsByStudent(username);
        if (exams.isEmpty()) {
            Label noScoresLabel = new Label("没有成绩记录。");
            getChildren().add(noScoresLabel);
        } else {
            for (Exam exam : exams) {
                exam.recalculateScore();
                Label scoreLabel = new Label("科目: " + exam.getSubject() + " 成绩: " + exam.getScore() + " 时间: " + exam.getTime());
                getChildren().add(scoreLabel);
            }
        }
        Button backButton = new Button("返回");
        backButton.setOnAction(e -> primaryStage.setScene(new Scene(new view.StudentView(primaryStage, username, questionController,userController), 600, 400)));
        getChildren().add(backButton);
    }
}