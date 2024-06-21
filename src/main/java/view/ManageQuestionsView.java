package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controller.*;
import model.Question;

public class ManageQuestionsView extends VBox {
    private final ListView<Question> questionListView;

    public ManageQuestionsView(Stage primaryStage, QuestionController questionController) {
        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(10);

        questionListView = new ListView<>();
        questionListView.getItems().addAll(questionController.getAllQuestions());

        Button deleteQuestionButton = new Button("删除题目");
        deleteQuestionButton.setOnAction(e -> {
            Question selectedQuestion = questionListView.getSelectionModel().getSelectedItem();
            if (selectedQuestion != null) {
                questionController.deleteQuestion(selectedQuestion.getId());
                questionListView.getItems().remove(selectedQuestion);
            }
        });

        Button backButton = new Button("返回");
        backButton.setOnAction(e -> primaryStage.setScene(new Scene(new view.AdminView(primaryStage, new UserController(), new ExamController(), questionController), 600, 400)));

        getChildren().addAll(questionListView, deleteQuestionButton, backButton);
    }
}
