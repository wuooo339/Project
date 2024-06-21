package view;
import controller.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static final UserController userController = new UserController();
    private static final QuestionController questionController = new QuestionController();
    private static final ExamController examController = new ExamController();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Smart Question Bank");

        // Start with the login view
        LoginView loginView = new LoginView(primaryStage, userController,questionController);
        Scene loginScene = new Scene(loginView, 600, 400);

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static UserController getUserController() {
        return userController;
    }

    public static QuestionController getQuestionController() {
        return questionController;
    }
    public static ExamController getExamController()
    {
        return examController;
    }

}
