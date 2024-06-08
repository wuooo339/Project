package view;

import controller.QuestionController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    private Button addButton;

    private QuestionController questionController = new QuestionController();

    @FXML
    private void initialize() {
        addButton.setOnAction(event -> addQuestion());
    }

    private void addQuestion() {
        String subject = subjectField.getText();
        String type = typeField.getText();
        String questionText = questionTextField.getText();
        String options = optionsField.getText();
        String correctAnswer = correctAnswerField.getText();
        model.Question question = new model.Question(subject, type, questionText, options, correctAnswer);
        questionController.addQuestion(question);
    }
}
