package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import controller.*;
import model.User;

import java.util.Objects;

public class LoginView extends GridPane {

    public LoginView(Stage primaryStage, UserController userController, QuestionController questionController) {
        setPadding(new Insets(20, 20, 20, 20));
        setVgap(15);
        setHgap(10);
        setAlignment(Pos.CENTER);

        // 背景颜色
        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        setBackground(background);

        // 标题
        Label titleLabel = new Label("登录界面");
        titleLabel.setFont(Font.font("KaiTi", 28));
        add(titleLabel, 0, 0, 2, 1);
        GridPane.setHalignment(titleLabel, HPos.CENTER);

        // 用户名图标
        ImageView usernameIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("username.png"))));
        usernameIcon.setFitHeight(24);
        usernameIcon.setFitWidth(24);

        // 用户名输入框
        TextField usernameInput = new TextField();
        usernameInput.setPromptText("用户名");
        HBox usernameBox = new HBox(10, usernameIcon, usernameInput);
        usernameBox.setAlignment(Pos.CENTER_LEFT);
        add(usernameBox, 1, 1);

        // 密码图标
        ImageView passwordIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("password.png"))));
        passwordIcon.setFitHeight(24);
        passwordIcon.setFitWidth(24);

        // 密码输入框
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("密码");
        HBox passwordBox = new HBox(10, passwordIcon, passwordInput);
        passwordBox.setAlignment(Pos.CENTER_LEFT);
        add(passwordBox, 1, 2);

        // 登录按钮
        Button loginButton = new Button("登录");
        loginButton.setFont(Font.font("KaiTi", 16));
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        loginButton.setOnAction(e -> handleLogin(primaryStage, userController, questionController, usernameInput.getText(), passwordInput.getText()));

        // 注册按钮
        Button registerButton = new Button("注册");
        registerButton.setFont(Font.font("KaiTi", 16));
        registerButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        registerButton.setOnAction(e -> primaryStage.setScene(new Scene(new RegisterView(primaryStage, userController, questionController), 600, 400)));

        // 按钮容器
        HBox buttonsBox = new HBox(20, loginButton, registerButton);
        buttonsBox.setAlignment(Pos.CENTER);
        add(buttonsBox, 1, 3);

        // 设置输入框事件处理
        usernameInput.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                loginButton.fire();
            }
        });

        passwordInput.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                loginButton.fire();
            }
        });
    }

    private void handleLogin(Stage primaryStage, UserController userController, QuestionController questionController, String username, String password) {
        User user = userController.login(username, password);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        if (user != null) {
            alert.setContentText("登录成功！");
            alert.show();

            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> {
                alert.hide();
                switch (user.getRole()) {
                    case "student":
                        primaryStage.setScene(new Scene(new StudentView(primaryStage, username, questionController, userController), 600, 400));
                        break;
                    case "professor":
                        primaryStage.setScene(new Scene(new ProfessorView(primaryStage, MainApp.getQuestionController(), userController), 600, 400));
                        break;
                    case "admin":
                        primaryStage.setScene(new Scene(new AdminView(primaryStage, userController, MainApp.getExamController(), MainApp.getQuestionController()), 600, 400));
                        break;
                }
            });
            delay.play();
        } else {
            alert.setContentText("用户名或密码错误!");
            alert.show();

            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> alert.hide());
            delay.play();
        }
    }
}
