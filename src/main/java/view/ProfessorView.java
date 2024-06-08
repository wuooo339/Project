package view;

import javafx.scene.control.ScrollPane;
import model.Question;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controller.QuestionController;
import controller.UserController;
import view.LoginView;
import java.io.IOException;
import java.util.List;

public class ProfessorView extends VBox {
    private QuestionController questionController;
    private UserController userController;

    public ProfessorView(Stage primaryStage, QuestionController questionController, UserController userController) {
        this.questionController = questionController;
        this.userController = userController;

        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(10);

        // Add Question Button
        Button addQuestionButton = new Button("录入题目 (CSV)");
        addQuestionButton.setOnAction(e -> primaryStage.setScene(new Scene(new AddQuestionView(primaryStage), 600, 400)));

        // View Questions Button
        Button viewQuestionsButton = new Button("查看题目");
        viewQuestionsButton.setOnAction(e -> primaryStage.setScene(new Scene(new ViewQuestionsView(primaryStage), 600, 400)));

        // Logout Button
        Button logoutButton = new Button("退出登录");
        logoutButton.setOnAction(e -> primaryStage.setScene(new Scene(new LoginView(primaryStage, userController,questionController), 400, 300)));

        getChildren().addAll(addQuestionButton, viewQuestionsButton, logoutButton);
    }

    class AddQuestionView extends VBox {
        public AddQuestionView(Stage primaryStage) {
            setPadding(new Insets(10, 10, 10, 10));
            setSpacing(10);

            Label fileLabel = new Label("CSV文件路径:");
            TextField filePathField = new TextField();
            Button uploadButton = new Button("上传");

            uploadButton.setOnAction(e -> {
                String filePath = filePathField.getText();
                try {
                    questionController.importQuestionsFromCSV(filePath);
                    primaryStage.setScene(new Scene(new ProfessorView(primaryStage, questionController, userController), 600, 400));
                } catch (IOException ex) {
                    ex.printStackTrace(); // 在实际应用中，可能需要更具体的处理方式
                }
            });

            getChildren().addAll(fileLabel, filePathField, uploadButton);
        }
    }

    class ViewQuestionsView extends VBox {
        public ViewQuestionsView(Stage primaryStage) {
            setPadding(new Insets(10, 10, 10, 10));
            setSpacing(10);

            Label subjectLabel = new Label("科目:");
            TextField subjectField = new TextField();
            Button viewButton = new Button("查看");

            VBox questionsBox = new VBox();
            questionsBox.setSpacing(10);
            ScrollPane scrollPane = new ScrollPane(questionsBox);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefHeight(300);

            viewButton.setOnAction(e -> {
                questionsBox.getChildren().clear(); // 清空之前的题目
                String subject = subjectField.getText();
                List<Question> questions = questionController.getQuestionsBySubject(subject);
                for (Question question : questions) {
                    Label questionLabel = new Label(question.getQuestionText());
                    questionsBox.getChildren().add(questionLabel);
                }
            });

            Button backButton = new Button("返回");
            backButton.setOnAction(e -> primaryStage.setScene(new Scene(new ProfessorView(primaryStage, questionController, userController), 600, 400)));

            getChildren().addAll(subjectLabel, subjectField, viewButton, scrollPane, backButton);
        }
    }
}
