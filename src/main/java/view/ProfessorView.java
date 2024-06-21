package view;

import controller.ExamController;
import controller.QuestionController;
import controller.UserController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.Question;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class ProfessorView extends GridPane {
    private final QuestionController questionController;
    private final UserController userController;
    private static final Logger LOGGER = Logger.getLogger(ProfessorView.class.getName());

    public ProfessorView(Stage primaryStage, QuestionController questionController, UserController userController) {
        this.questionController = questionController;
        this.userController = userController;

        setPadding(new Insets(10, 10, 10, 10));
        setHgap(20);
        setVgap(20);
        setAlignment(Pos.CENTER);

        // 加载 CSS 样式
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("profess-view.css")).toExternalForm());


        // Add Question Button
        VBox addQuestionBox = createActionBox("录入题目 (CSV)", "folder.png");
        addQuestionBox.setOnMouseClicked(e -> primaryStage.setScene(new Scene(new AddQuestionCSVView(primaryStage), 600, 400)));

        // Add Individual Question Button
        VBox addIndividualQuestionBox = createActionBox("录入单个题目", "edit.png");
        addIndividualQuestionBox.setOnMouseClicked(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddQuestionView.fxml"));
                VBox addQuestionPane = loader.load();
                AddQuestionView addQuestionView = loader.getController();
                addQuestionView.setQuestionController(questionController); // 设置控制器
                addQuestionView.setPrimaryStage(primaryStage);
                primaryStage.setScene(new Scene(addQuestionPane, 600, 400));
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Failed to load AddQuestionView.fxml", ex);
            }
        });

        // View Questions Button
        VBox viewQuestionsBox = createActionBox("查看题目", "view_questions.png");
        viewQuestionsBox.setOnMouseClicked(e -> primaryStage.setScene(new Scene(new ViewQuestionsView(primaryStage), 600, 400)));

        // View Scores Button
        VBox viewScoresBox = createActionBox("查看成绩", "view_scores.png");
        viewScoresBox.setOnMouseClicked(e -> primaryStage.setScene(new Scene(new ViewScoresView(primaryStage, null, new ExamController(), questionController, userController, true), 800, 800)));

        // Add action boxes to the main layout
        add(addQuestionBox, 0, 0);
        add(addIndividualQuestionBox, 1, 0);
        add(viewQuestionsBox, 0, 1);
        add(viewScoresBox, 1, 1);

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

        // 将 logoutButton 添加到当前 GridPane 的右上角
        add(logoutButton, 0, 2); // 假设你已经添加了足够的列，或者使用一个合适的列索引
    }

    private VBox createActionBox(String title, String iconPath) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("action-box");

        ImageView icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(iconPath))));
        icon.setFitWidth(50);
        icon.setFitHeight(50);

        Label label = new Label(title);
        label.getStyleClass().add("action-box-label");

        box.getChildren().addAll(icon, label);

        // Add hover effect
        box.setOnMouseEntered(e -> box.setStyle("-fx-background-color: #e0e0e0;"));
        box.setOnMouseExited(e -> box.setStyle("-fx-background-color: white;"));

        return box;
    }
    class AddQuestionCSVView extends VBox {
        public AddQuestionCSVView(Stage primaryStage) {
            setPadding(new Insets(10, 10, 10, 10));
            setSpacing(10);

            Label fileLabel = new Label("CSV文件路径:");
            TextField filePathField = new TextField();
            Button uploadButton = new Button("上传");
            uploadButton.getStyleClass().add("button");

            uploadButton.setOnAction(e -> {
                String filePath = filePathField.getText();
                try {
                    questionController.importQuestionsFromCSV(filePath);
                    primaryStage.setScene(new Scene(new ProfessorView(primaryStage, questionController, userController), 600, 400));
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, "Failed to import questions from CSV", ex);
                }
            });

            Button backButton = new Button("退出");
            backButton.setOnAction(e -> {
                primaryStage.setScene(new Scene(new ProfessorView(primaryStage, questionController, userController), 600, 400));
            });

            getChildren().addAll(fileLabel, filePathField, uploadButton, backButton);
        }
    }
    class ViewQuestionsView extends VBox {
        public ViewQuestionsView(Stage primaryStage) {
            setPadding(new Insets(10, 10, 10, 10));
            setSpacing(10);

            Label subjectLabel = new Label("科目:");
            ComboBox<String> subjectComboBox = new ComboBox<>();
            subjectComboBox.getItems().addAll("Math", "Science", "History", "English", "Geography", "Computer");
            Button viewButton = new Button("查看");
            viewButton.getStyleClass().add("button");

            VBox questionsBox = new VBox();
            questionsBox.setSpacing(10);
            ScrollPane scrollPane = new ScrollPane(questionsBox);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefHeight(300);

            viewButton.setOnAction(e -> {
                questionsBox.getChildren().clear(); // 清空之前的题目
                String subject = subjectComboBox.getValue();
                List<Question> questions = questionController.getQuestionsBySubject(subject);
                for (Question question : questions) {
                    Label questionLabel = new Label(question.getQuestionText());
                    questionsBox.getChildren().add(questionLabel);
                }
            });

            Button backButton = new Button("返回");
            backButton.getStyleClass().add("button");
            backButton.setOnAction(e -> primaryStage.setScene(new Scene(new ProfessorView(primaryStage, questionController, userController), 600, 400)));

            getChildren().addAll(subjectLabel, subjectComboBox, viewButton, scrollPane, backButton);
        }
    }


}