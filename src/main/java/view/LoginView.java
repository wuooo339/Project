package view;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
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
import model.User;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
public class LoginView extends GridPane {

    private UserController userController;
    private QuestionController questionController;

    public LoginView(Stage primaryStage, UserController userController, QuestionController questionController) {
        this.userController = userController;
        this.questionController = questionController;
        setPadding(new Insets(20, 20, 20, 20));
        setVgap(10);
        setHgap(10);
        setAlignment(Pos.CENTER);

        // 背景颜色
        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        setBackground(background);

        // 标题
        Label titleLabel = new Label("登录界面");
        titleLabel.setFont(new Font("Arial", 24));
        add(titleLabel, 0, 0, 2, 1);
        GridPane.setHalignment(titleLabel, HPos.CENTER);

        // 用户名
        Label usernameLabel = new Label("用户名:");
        GridPane.setHalignment(usernameLabel, HPos.RIGHT);
        TextField usernameInput = new TextField();
        GridPane.setHalignment(usernameInput, HPos.LEFT);
        add(usernameLabel, 0, 1);
        add(usernameInput, 1, 1);

        // 密码
        Label passwordLabel = new Label("密码:");
        GridPane.setHalignment(passwordLabel, HPos.RIGHT);
        PasswordField passwordInput = new PasswordField();
        GridPane.setHalignment(passwordInput, HPos.LEFT);
        add(passwordLabel, 0, 2);
        add(passwordInput, 1, 2);

        // 登录按钮
        Button loginButton = new Button("登录");
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        loginButton.setOnAction(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            User user = userController.login(username, password);
            if (user != null) {
                Label successLabel = new Label("登录成功！");
                successLabel.setTextFill(Color.GREEN);
                add(successLabel, 1, 4);
                if (user.getRole().equals("student")) {
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(event -> primaryStage.setScene(new Scene(new StudentView(primaryStage, username, questionController, userController), 600, 400)));
                    delay.play();
                } else if (user.getRole().equals("professor")) {
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(event -> primaryStage.setScene(new Scene(new ProfessorView(primaryStage, MainApp.getQuestionController(), userController), 600, 400)));
                    delay.play();
                } else if (user.getRole().equals("admin")) {
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(event -> primaryStage.setScene(new Scene(new AdminView(primaryStage, userController, MainApp.getExamController(), MainApp.getQuestionController()), 600, 400)));
                    delay.play();
                }
            } else {
                Label failureLabel = new Label("用户名或密码错误!");
                failureLabel.setTextFill(Color.RED);
                add(failureLabel, 1, 4);
                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished(event -> getChildren().remove(failureLabel));
                delay.play();
            }
        });

        // 注册按钮
        Button registerButton = new Button("注册");
        registerButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        registerButton.setOnAction(e -> primaryStage.setScene(new Scene(new RegisterView(primaryStage, userController, questionController), 400, 300)));

        HBox buttons = new HBox(10, loginButton, registerButton);
        buttons.setAlignment(Pos.CENTER);
        add(buttons, 1, 3);

        usernameInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    loginButton.fire(); // 触发登录按钮的事件
                }
            }
        });

        passwordInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    loginButton.fire(); // 触发登录按钮的事件
                }
            }
        });
    }
}
