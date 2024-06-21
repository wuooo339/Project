package view;

import controller.ExamController;
import controller.QuestionController;
import controller.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.logging.Logger;

public class StudentView extends GridPane {
    private static final Logger LOGGER = Logger.getLogger(StudentView.class.getName());
    private final ExamController examController;

    public StudentView(Stage primaryStage, String username, QuestionController questionController, UserController userController) {
        this.examController = new ExamController();

        setPadding(new Insets(10, 10, 10, 10));
        setHgap(20);
        setVgap(20);
        setAlignment(Pos.CENTER);

        // Load CSS styles
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("student-view.css")).toExternalForm());

        // Create action boxes
        VBox answerQuestionsBox = createActionBox("开始考试", "exam.png", e -> primaryStage.setScene(new Scene(new AnswerQuestionsView(primaryStage, username, examController, userController), 900, 600)), true);
        VBox viewScoresBox = createActionBox("查看成绩", "score.png", e -> primaryStage.setScene(new Scene(new ViewScoresView(primaryStage, username, examController, questionController, userController, false), 800, 800)), false);

        // Arrange action boxes
        add(answerQuestionsBox, 0, 0);
        add(viewScoresBox, 1, 0);

        // Logout Button
        Button logoutButton = new Button("退出登录");
        logoutButton.setTextFill(Paint.valueOf("BLACK"));
        logoutButton.getStyleClass().add("black-text-button");
        logoutButton.getStyleClass().add("logout-button");
        ImageView logoutIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("logout.png"))));
        logoutIcon.setFitWidth(20);
        logoutIcon.setFitHeight(20);
        logoutButton.setGraphic(logoutIcon);
        logoutButton.setOnAction(e -> primaryStage.setScene(new Scene(new LoginView(primaryStage, userController, questionController), 600, 400)));

        // Add logout button to the lower left corner
        add(logoutButton, 0, 1); // Adjust as necessary
    }

    private VBox createActionBox(String title, String icon, EventHandler<ActionEvent> eventHandler, boolean isLarge) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("action-box");

        ImageView iconView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(icon))));
        iconView.setFitWidth(50);
        iconView.setFitHeight(50);

        Label label = new Label(title);
        label.getStyleClass().add("action-box-label");

        box.getChildren().addAll(iconView, label);

        // Add hover effect
        box.setOnMouseEntered(e -> box.setStyle("-fx-background-color: #e0e0e0;"));
        box.setOnMouseExited(e -> box.setStyle("-fx-background-color: white;"));

        box.setOnMouseClicked(new MouseEventToActionEventAdapter(eventHandler));


        if (isLarge) {
            box.getStyleClass().add("large-action-box");
        }

        return box;
    }
    private static class MouseEventToActionEventAdapter implements EventHandler<MouseEvent> {
        private final EventHandler<ActionEvent> eventHandler;

        public MouseEventToActionEventAdapter(EventHandler<ActionEvent> eventHandler) {
            this.eventHandler = eventHandler;
        }

        @Override
        public void handle(MouseEvent event) {
            ActionEvent actionEvent = new ActionEvent(event.getSource(), event.getTarget());
            eventHandler.handle(actionEvent);
        }
    }

}
