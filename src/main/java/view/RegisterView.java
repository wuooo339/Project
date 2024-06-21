package view;

import controller.*;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class RegisterView extends StackPane {

    private Label messageLabel;

    public RegisterView(Stage primaryStage, UserController userController, QuestionController questionController) {
        // 设置背景
        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        setBackground(background);

        // 创建主要内容面板
        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(30));
        contentBox.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        contentBox.setMaxWidth(400);
        contentBox.setEffect(new DropShadow(10, Color.BLACK));

        // 标题
        Label titleLabel = new Label("用户注册");
        titleLabel.setFont(Font.font("KaiTi", 24));
        titleLabel.setTextFill(Color.web("#1e3c72"));

        // 表单
        GridPane form = createForm(userController, questionController, primaryStage);

        // 消息标签
        messageLabel = new Label();
        messageLabel.setTextFill(Color.RED);

        contentBox.getChildren().addAll(titleLabel, form, messageLabel);
        getChildren().add(contentBox);
    }

    private GridPane createForm(UserController userController, QuestionController questionController, Stage primaryStage) {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(15);
        form.setAlignment(Pos.CENTER);

        // 用户名
        Label usernameLabel = new Label("用户名:");
        usernameLabel.setFont(Font.font("KaiTi", 16));
        TextField usernameInput = createStyledTextField("请输入用户名");
        form.add(usernameLabel, 0, 0);
        form.add(usernameInput, 1, 0);

        // 密码
        Label passwordLabel = new Label("密码:");
        passwordLabel.setFont(Font.font("KaiTi", 16));
        PasswordField passwordInput = createStyledPasswordField("请输入密码");
        form.add(passwordLabel, 0, 1);
        form.add(passwordInput, 1, 1);

        // 角色
        Label roleLabel = new Label("角色:");
        roleLabel.setFont(Font.font("KaiTi", 16));
        ComboBox<String> roleInput = createStyledComboBox();
        form.add(roleLabel, 0, 2);
        form.add(roleInput, 1, 2);

        // 按钮
        Button registerButton = createStyledButton("注册", "#4CAF50");
        registerButton.setFont(Font.font("KaiTi", 16));
        Button backButton = createStyledButton("返回", "#f44336");
        backButton.setFont(Font.font("KaiTi", 16));

        registerButton.setOnAction(e -> register(userController, usernameInput, passwordInput, roleInput, primaryStage, questionController));
        backButton.setOnAction(e -> primaryStage.setScene(new Scene(new LoginView(primaryStage, userController, questionController), 600, 400)));

        HBox buttonBox = new HBox(20, registerButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);
        form.add(buttonBox, 0, 3, 2, 1);

        return form;
    }

    private TextField createStyledTextField(String prompt) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 5; -fx-border-radius: 5;");
        return textField;
    }

    private PasswordField createStyledPasswordField(String prompt) {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(prompt);
        passwordField.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 5; -fx-border-radius: 5;");
        return passwordField;
    }

    private ComboBox<String> createStyledComboBox() {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("学生", "教师", "管理员");
        comboBox.setPromptText("请选择角色");
        comboBox.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 5; -fx-border-radius: 5;");
        return comboBox;
    }

    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");

        // 添加悬停效果
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(" + color + ", 20%); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;"));

        // 添加点击动画
        button.setOnMousePressed(e -> button.setStyle("-fx-background-color: derive(" + color + ", -20%); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;"));
        button.setOnMouseReleased(e -> button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;"));

        return button;
    }

    private void register(UserController userController, TextField usernameInput, PasswordField passwordInput, ComboBox<String> roleInput, Stage primaryStage, QuestionController questionController) {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        String role = roleInput.getValue();
        if (role == null) {
            showMessage("请选择一个角色。");
            return;
        }
        String actualRole = convertRole(role);
        boolean success = userController.register(username, password, actualRole);
        if (success) {
            showMessage("注册成功！" + username);
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> primaryStage.setScene(new Scene(new LoginView(primaryStage, userController, questionController), 600, 400)));
            delay.play();
        } else {
            showMessage("注册失败，请重试。");
        }
    }

    private String convertRole(String displayRole) {
        switch (displayRole) {
            case "学生": return "student";
            case "教师": return "professor";
            case "管理员": return "admin";
            default: return "";
        }
    }

    private void showMessage(String message) {
        messageLabel.setText(message);
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> messageLabel.setText(""));
        delay.play();
    }
}