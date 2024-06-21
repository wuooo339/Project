package view;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import controller.UserController;
import controller.ExamController;
import controller.QuestionController;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Objects;

public class AdminView extends BorderPane {

    private static final Logger LOGGER = Logger.getLogger(AdminView.class.getName());

    public AdminView(Stage primaryStage, UserController userController, ExamController examController, QuestionController questionController) {
        setPadding(new Insets(20));

        // 加载 CSS 样式
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/admin-view.css")).toExternalForm());

        // 创建主要功能区
        HBox mainFunctions = new HBox(20);
        mainFunctions.setAlignment(Pos.CENTER);
        mainFunctions.getChildren().addAll(
                createFunctionBox("管理用户", "/images/user.png", e -> loadView(primaryStage, new ManageUsersView(primaryStage, userController))),
                createFunctionBox("管理成绩", "/images/exam.png", e -> loadView(primaryStage, new ManageExamsView(primaryStage, examController))),
                createFunctionBox("管理题目", "/images/questions_icon.png", e -> loadView(primaryStage, new ManageQuestionsView(primaryStage, questionController)))
        );
        setCenter(mainFunctions);

        // 创建退出登录按钮
        StackPane logoutBox = createLogoutBox(e -> loadView(primaryStage, new LoginView(primaryStage, userController, questionController)));
        setBottom(logoutBox);
        BorderPane.setAlignment(logoutBox, Pos.BOTTOM_LEFT);
        BorderPane.setMargin(logoutBox, new Insets(20, 0, 0, 0));
    }

    private StackPane createFunctionBox(String label, String iconPath, javafx.event.EventHandler<javafx.scene.input.MouseEvent> clickHandler) {
        StackPane box = new StackPane();
        box.setPrefSize(200, 200);
        box.getStyleClass().add("function-box");

        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);

        ImageView icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(iconPath))));
        icon.setFitHeight(80);
        icon.setFitWidth(80);

        Label textLabel = new Label(label);
        textLabel.getStyleClass().add("function-label");

        content.getChildren().addAll(icon, textLabel);
        box.getChildren().add(content);

        // 添加动画效果
        addHoverAnimation(box);

        box.setOnMouseClicked(clickHandler);

        return box;
    }

    private StackPane createLogoutBox(javafx.event.EventHandler<javafx.scene.input.MouseEvent> clickHandler) {
        StackPane box = new StackPane();
        box.setPrefSize(100, 100);
        box.getStyleClass().add("logout-box");

        VBox content = new VBox(5);
        content.setAlignment(Pos.CENTER);

        ImageView icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logout.png"))));
        icon.setFitHeight(40);
        icon.setFitWidth(40);

        Label textLabel = new Label("退出登录");
        textLabel.getStyleClass().add("logout-label");

        content.getChildren().addAll(icon, textLabel);
        box.getChildren().add(content);

        // 添加动画效果
        addHoverAnimation(box);

        box.setOnMouseClicked(clickHandler);

        return box;
    }

    private void addHoverAnimation(StackPane box) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), box);
        box.setOnMouseEntered(e -> {
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.playFromStart();
        });
        box.setOnMouseExited(e -> {
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            scaleTransition.playFromStart();
        });
    }

    private void loadView(Stage primaryStage, javafx.scene.Parent view) {
        try {
            primaryStage.setScene(new Scene(view, 600, 400));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to load view", ex);
        }
    }
}