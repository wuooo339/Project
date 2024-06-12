package view;
import view.MainApp;
import controller.UserController;
import controller.QuestionController;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.StudentView;
import view.ProfessorView;
import view.RegisterView;
import view.AdminView;

import model.User;
public class LoginView extends GridPane {

    private UserController userController;
    private QuestionController questionController;
    public LoginView(Stage primaryStage, UserController userController, QuestionController questionController) {
        this.userController = userController;
        this.questionController  = questionController;
        setPadding(new Insets(10, 10, 10, 10));
        setVgap(8);
        setHgap(10);

        // Username
        Label usernameLabel = new Label("用户名:");
        TextField usernameInput = new TextField();
        add(usernameLabel, 0, 0);
        add(usernameInput, 1, 0);

        // Password
        Label passwordLabel = new Label("密码:");
        PasswordField passwordInput = new PasswordField();
        add(passwordLabel, 0, 1);
        add(passwordInput, 1, 1);

        // Login Button
        Button loginButton = new Button("登录");
        loginButton.setOnAction(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            User user = userController.login(username, password);
            if (user != null) {
                Label successLabel = new Label("登录成功！");
                add(successLabel, 1, 3);
                if (user.getRole().equals("student")) {
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(event -> primaryStage.setScene(new Scene(new StudentView(primaryStage, username,questionController,userController), 600, 400)));
                    delay.play();
                } else if (user.getRole().equals("professor")) {
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(event -> primaryStage.setScene(new Scene(new ProfessorView(primaryStage,MainApp.getQuestionController(),userController), 600, 400)));
                    delay.play();
                }
                else if (user.getRole().equals("admin")) {
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(event -> primaryStage.setScene(new Scene(new AdminView(primaryStage,userController,MainApp.getExamController(),MainApp.getQuestionController()), 600, 400)));
                    delay.play();
                }
            } else {
                Label failureLabel = new Label("用户名或密码错误!");
                add(failureLabel, 1, 3);
                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished(event -> getChildren().remove(failureLabel));
                delay.play();
            }
        });
        // Register Button
        Button registerButton = new Button("注册");
        registerButton.setOnAction(e -> primaryStage.setScene(new Scene(new RegisterView(primaryStage,userController,questionController), 400, 300)));

        HBox buttons = new HBox(10, loginButton, registerButton);
        add(buttons, 1, 2);
    }
}
