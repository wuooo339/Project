package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controller.UserController;
import controller.ExamController;
import controller.QuestionController;
import view.ManageUsersView;
import view.ManageExamsView;
import view.ManageQuestionsView;


public class AdminView extends VBox {
    private UserController userController;
    private ExamController examController;
    private QuestionController questionController;

    public AdminView(Stage primaryStage, UserController userController, ExamController examController, QuestionController questionController) {
        this.userController = userController;
        this.examController = examController;
        this.questionController = questionController;
        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(10);

        // Manage Users Button
        Button manageUsersButton = new Button("管理用户");
        manageUsersButton.setOnAction(e -> primaryStage.setScene(new Scene(new ManageUsersView(primaryStage, userController), 600, 400)));

        // Manage Exams Button
        Button manageExamsButton = new Button("管理成绩");
        manageExamsButton.setOnAction(e -> primaryStage.setScene(new Scene(new ManageExamsView(primaryStage, examController), 600, 400)));

        // Manage Questions Button
        Button manageQuestionsButton = new Button("管理题目");
        manageQuestionsButton.setOnAction(e -> primaryStage.setScene(new Scene(new ManageQuestionsView(primaryStage, questionController), 600, 400)));
        // Logout Button
        Button logoutButton = new Button("退出登录");
        logoutButton.setOnAction(e -> {
            // Redirect to login screen (assuming LoginView is your login screen)
            primaryStage.setScene(new Scene(new view.LoginView(primaryStage, userController,questionController), 600, 400));
        });

        // Add buttons to layout
        getChildren().addAll(manageUsersButton, manageExamsButton, manageQuestionsButton,logoutButton);
    }
}
