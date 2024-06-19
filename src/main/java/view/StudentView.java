package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controller.*;

public class StudentView extends VBox {
    private String username;
    private UserController userController;
    private static ExamController examController = new ExamController();

    public StudentView(Stage primaryStage, String username, QuestionController questionController, UserController userController) {
        this.username = username;
        this.userController = userController;

        // 加载 CSS 样式
        getStylesheets().add(getClass().getResource("student-view.css").toExternalForm());

        // 创建根节点
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.getStyleClass().add("root");

        // 创建按钮
        Button answerQuestionsButton = new Button("选择科目并答题");
        Button viewScoresButton = new Button("查看成绩");
        Button logoutButton = new Button("退出登录");

        // 将按钮添加到 HBox 中
        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(answerQuestionsButton, viewScoresButton, logoutButton);

        // 将 HBox 添加到根节点
        root.getChildren().add(buttonBox);

        // 设置事件处理
        answerQuestionsButton.setOnAction(e -> {
            primaryStage.setScene(new Scene(new AnswerQuestionsView(primaryStage, username, examController, userController), 900, 600));
        });

        viewScoresButton.setOnAction(e -> {
            primaryStage.setScene(new Scene(new ViewScoresView(primaryStage, username, examController, questionController, userController, false), 800, 800));
        });

        logoutButton.setOnAction(e -> {
            primaryStage.setScene(new Scene(new LoginView(primaryStage, userController, questionController), 600, 400));
        });
        getChildren().add(root);
    }
}