package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import controller.UserController;
import controller.ExamController;
import controller.QuestionController;

public class AdminView extends GridPane {
    private UserController userController;
    private ExamController examController;
    private QuestionController questionController;

    public AdminView(Stage primaryStage, UserController userController, ExamController examController, QuestionController questionController) {
        this.userController = userController;
        this.examController = examController;
        this.questionController = questionController;

        setPadding(new Insets(20, 20, 20, 20));
        setHgap(20);
        setVgap(20);
        setAlignment(Pos.CENTER);

        // 加载 CSS 样式
        getStylesheets().add(getClass().getResource("/admin-view.css").toExternalForm());

        // 标题
        Label titleLabel = new Label("管理员界面");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.getStyleClass().add("title");

        // Manage Users Button
        Button manageUsersButton = new Button("管理用户");
        manageUsersButton.getStyleClass().add("button");
        manageUsersButton.setOnAction(e -> primaryStage.setScene(new Scene(new ManageUsersView(primaryStage, userController), 600, 400)));

        // Manage Exams Button
        Button manageExamsButton = new Button("管理成绩");
        manageExamsButton.getStyleClass().add("button");
        manageExamsButton.setOnAction(e -> primaryStage.setScene(new Scene(new ManageExamsView(primaryStage, examController), 600, 400)));

        // Manage Questions Button
        Button manageQuestionsButton = new Button("管理题目");
        manageQuestionsButton.getStyleClass().add("button");
        manageQuestionsButton.setOnAction(e -> primaryStage.setScene(new Scene(new ManageQuestionsView(primaryStage, questionController), 600, 400)));

        // Logout Button
        Button logoutButton = new Button("退出登录");
        logoutButton.getStyleClass().add("button");
        logoutButton.setOnAction(e -> primaryStage.setScene(new Scene(new LoginView(primaryStage, userController, questionController), 600, 400)));

        // 添加按钮和标题到布局
        add(titleLabel, 0, 0, 2, 1);
        add(manageUsersButton, 0, 1);
        add(manageExamsButton, 1, 1);
        add(manageQuestionsButton, 0, 2);
        add(logoutButton, 1, 2);
    }
}