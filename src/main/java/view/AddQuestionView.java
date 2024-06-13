package view;
import controller.UserController;
import javafx.scene.Scene;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import controller.QuestionController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
public class AddQuestionView {

    @FXML
    private TextField subjectField;

    @FXML
    private TextField typeField;

    @FXML
    private TextArea questionTextField;

    @FXML
    private TextField optionsField;

    @FXML
    private TextField correctAnswerField;

    @FXML
    private TextField difficultyField;

    @FXML
    private Button addButton;

    @FXML
    private Label feedbackLabel;

    @FXML
    private Button backButton; // 新增返回按钮

    private QuestionController questionController;
    private Stage primaryStage;

    @FXML
    private void initialize() {
        addButton.setOnAction(event -> addQuestion());
        backButton.setOnAction(event -> goBack()); // 设置返回按钮的事件处理程序
    }

    public void setQuestionController(QuestionController questionController) {
        this.questionController = questionController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void addQuestion() {
        String subject = subjectField.getText();
        String type = typeField.getText();
        String questionText = questionTextField.getText();
        String options = optionsField.getText();
        String correctAnswer = correctAnswerField.getText();
        int difficulty;

        try {
            difficulty = Integer.parseInt(difficultyField.getText().trim());
        } catch (NumberFormatException e) {
            feedbackLabel.setText("难度必须是整数");
            return;
        }

        try {
            model.Question question = new model.Question(subject, type, questionText, options, correctAnswer, difficulty);
            questionController.addQuestion(question);
            feedbackLabel.setText("题目添加成功");
        } catch (Exception e) {
            feedbackLabel.setText("题目添加失败");
            e.printStackTrace();
        }
        clearFeedbackAfterDelay();
    }

    private void clearFeedbackAfterDelay() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(1),
                event -> feedbackLabel.setText("")));
        timeline.play();
    }

    private void goBack() {
        primaryStage.setScene(new Scene(new ProfessorView(primaryStage, questionController, new UserController()), 600, 400));
    }
}
