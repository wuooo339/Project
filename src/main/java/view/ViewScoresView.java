package view;

import controller.ExamController;
import controller.QuestionController;
import controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Exam;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewScoresView extends VBox {
    private ExamController examController;
    private QuestionController questionController;
    private UserController userController;

    public ViewScoresView(Stage primaryStage, String username, ExamController examController, UserController userController) {
        this.examController = examController;
        this.userController = userController;
        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(10);

        Label subjectLabel = new Label("选择科目:");
        ComboBox<String> subjectComboBox = new ComboBox<>();
        subjectComboBox.getItems().addAll("Math", "Science", "History", "English","Geography","Computer");

        Button showScoresButton = new Button("显示成绩");
        ScrollPane scrollPane = new ScrollPane();
        VBox scoresBox = new VBox(10);
        scrollPane.setContent(scoresBox);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        showScoresButton.setOnAction(e -> {
            String subject = subjectComboBox.getValue();
            List<Exam> exams = examController.getExamsByStudentAndSubject(username, subject);
            scoresBox.getChildren().clear();

            if (exams.isEmpty()) {
                Label noScoresLabel = new Label("没有该科目的成绩记录。");
                scoresBox.getChildren().add(noScoresLabel);
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (Exam exam : exams) {
                    exam.recalculateScore();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String formattedDateTime = exam.getTime().format(formatter);
                    Label scoreLabel = new Label("科目: " + exam.getSubject() + " 成绩: " + exam.getScore() + " 时间: " +formattedDateTime);
                    scoresBox.getChildren().add(scoreLabel);
                }
            }
        });

        Button backButton = new Button("返回");
        backButton.setOnAction(e -> primaryStage.setScene(new Scene(new view.StudentView(primaryStage, username, questionController, userController), 900, 600)));

        PieChart pieChart = new PieChart();
        Button analyzeButton = new Button("分析成绩");
        analyzeButton.setOnAction(e -> {
            List<Exam> allExams = examController.getExamsByStudent(username);
            Map<String, Integer> subjectCount = new HashMap<>();
            Map<String, Integer> scoreSum = new HashMap<>();

            for (Exam exam : allExams) {
                String subject = exam.getSubject();
                int score = exam.getScore();
                subjectCount.put(subject, subjectCount.getOrDefault(subject, 0) + 1);
                scoreSum.put(subject, scoreSum.getOrDefault(subject, 0) + score);
            }

            pieChart.getData().clear();
            for (String subject : subjectCount.keySet()) {
                int count = subjectCount.get(subject);
                int totalScore = scoreSum.get(subject);
                double avgScore = (double) totalScore / count;
                PieChart.Data slice = new PieChart.Data(subject + " (平均成绩: " + avgScore + ")", count);
                pieChart.getData().add(slice);
            }
        });

        getChildren().addAll(subjectLabel, subjectComboBox, showScoresButton, scrollPane, backButton, analyzeButton, pieChart);
    }
}
