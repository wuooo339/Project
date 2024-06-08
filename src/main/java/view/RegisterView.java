package view;

import controller.UserController;
import controller.QuestionController;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.LoginView;
public class RegisterView extends GridPane {

    private UserController userController;
    private QuestionController questionController;
    public RegisterView(Stage primaryStage, UserController userController, QuestionController questionController) {
        this.userController = userController;
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

        // Role
        Label roleLabel = new Label("角色:");
        ComboBox<String> roleInput = new ComboBox<>();
        roleInput.getItems().addAll("student", "professor");
        add(roleLabel, 0, 2);
        add(roleInput, 1, 2);

        // Register Button
        Button registerButton = new Button("注册");
        registerButton.setOnAction(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            String role = roleInput.getValue();
            if (role == null) {
                Label failureLabel = new Label("请选择一个角色。");
                add(failureLabel, 1, 3);
                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished(event -> getChildren().remove(failureLabel));
                delay.play();
                return;
            }
            boolean success = userController.register(username, password, role);
            if (success) {
                Label successLabel = new Label("注册成功！" + username);
                add(successLabel, 1, 3);
                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished(event -> primaryStage.setScene(new Scene(new LoginView(primaryStage, userController,questionController), 400, 300)));
                delay.play();
            } else {
                Label failureLabel = new Label("注册失败，请重试。");
                add(failureLabel, 1, 3);
                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished(event -> getChildren().remove(failureLabel));
                delay.play();
            }
        });

        HBox buttons = new HBox(10, registerButton);
        add(buttons, 1, 4);
    }
}
