package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controller.ExamController;
import model.Exam;
import controller.UserController;
import controller.QuestionController;

public class ManageExamsView extends VBox {
    private ExamController examController;
    private ListView<Exam> examListView;

    public ManageExamsView(Stage primaryStage, ExamController examController) {
        this.examController = examController;
        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(10);

        examListView = new ListView<>();
        examListView.getItems().addAll(examController.getAllExams());

        Button deleteExamButton = new Button("删除成绩");
        deleteExamButton.setOnAction(e -> {
            Exam selectedExam = examListView.getSelectionModel().getSelectedItem();
            if (selectedExam != null) {
                examController.deleteExam(selectedExam.getId());
                examListView.getItems().remove(selectedExam);
            }
        });

        Button backButton = new Button("返回");
        backButton.setOnAction(e -> primaryStage.setScene(new Scene(new view.AdminView(primaryStage, new UserController(), examController, new QuestionController()), 600, 400)));

        getChildren().addAll(examListView, deleteExamButton, backButton);
    }
}
