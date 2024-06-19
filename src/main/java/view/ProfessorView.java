package view;

import controller.ExamController;
import controller.QuestionController;
import controller.UserController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Question;

import java.io.IOException;
import java.util.List;

public class ProfessorView extends GridPane {
    private QuestionController questionController;
    private UserController userController;

    public ProfessorView(Stage primaryStage, QuestionController questionController, UserController userController) {
        this.questionController = questionController;
        this.userController = userController;

        setPadding(new Insets(10, 10, 10, 10));
        setHgap(10);
        setVgap(10);
        setAlignment(Pos.CENTER);

        // 加载 CSS 样式
        getStylesheets().add(getClass().getResource("/profess-view.css").toExternalForm());

        // Add Question Button
        Button addQuestionButton = new Button("录入题目 (CSV)");
        addQuestionButton.getStyleClass().add("button");
        addQuestionButton.setOnAction(e -> primaryStage.setScene(new Scene(new AddQuestionCSVView(primaryStage), 600, 400)));

        // Add Individual Question Button
        Button addIndividualQuestionButton = new Button("录入单个题目");
        addIndividualQuestionButton.getStyleClass().add("button");
        addIndividualQuestionButton.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddQuestionView.fxml"));
                VBox addQuestionPane = loader.load();
                AddQuestionView addQuestionView = loader.getController();
                addQuestionView.setQuestionController(questionController); // 设置控制器
                addQuestionView.setPrimaryStage(primaryStage);
                primaryStage.setScene(new Scene(addQuestionPane, 600, 400));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // View Questions Button
        Button viewQuestionsButton = new Button("查看题目");
        viewQuestionsButton.getStyleClass().add("button");
        viewQuestionsButton.setOnAction(e -> primaryStage.setScene(new Scene(new ViewQuestionsView(primaryStage), 600, 400)));

        // View Scores Button
        Button viewScoresButton = new Button("查看成绩");
        viewScoresButton.getStyleClass().add("button");
        viewScoresButton.setOnAction(e -> primaryStage.setScene(new Scene(new ViewScoresView(primaryStage, null, new ExamController(), questionController, userController, true), 800, 800)));

        // Logout Button
        Button logoutButton = new Button("退出登录");
        logoutButton.getStyleClass().add("button");
        logoutButton.setOnAction(e -> primaryStage.setScene(new Scene(new LoginView(primaryStage, userController, questionController), 600, 400)));

        add(addQuestionButton, 0, 0);
        add(addIndividualQuestionButton, 1, 0);
        add(viewQuestionsButton, 0, 1);
        add(viewScoresButton, 1, 1);
        add(logoutButton, 0, 2, 2, 1); // 让 "退出登录" 按钮占据两列
    }

    class AddQuestionCSVView extends VBox {
        public AddQuestionCSVView(Stage primaryStage) {
            setPadding(new Insets(10, 10, 10, 10));
            setSpacing(10);

            Label fileLabel = new Label("CSV文件路径:");
            TextField filePathField = new TextField();
            Button uploadButton = new Button("上传");
            uploadButton.getStyleClass().add("button");

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
            viewButton.getStyleClass().add("button");

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
            backButton.getStyleClass().add("button");
            backButton.setOnAction(e -> primaryStage.setScene(new Scene(new ProfessorView(primaryStage, questionController, userController), 600, 400)));

            getChildren().addAll(subjectLabel, subjectField, viewButton, scrollPane, backButton);
        }
    }
}