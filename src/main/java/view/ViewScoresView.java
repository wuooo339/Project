package view;

import controller.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Exam;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewScoresView extends VBox {
    private ExamController examController;
    private QuestionController questionController;
    private UserController userController;
    private ComboBox<String> subjectComboBox;
    private VBox scoresBox;
    private boolean isTeacher;

    public ViewScoresView(Stage primaryStage, String username, ExamController examController, QuestionController questionController, UserController userController, boolean isTeacher) {
        this.examController = examController;
        this.questionController = questionController;
        this.userController = userController;
        this.isTeacher = isTeacher;
        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(10);

        Label subjectLabel = new Label("选择科目:");
        subjectComboBox = new ComboBox<>();
        subjectComboBox.getItems().addAll("Math", "Science", "History", "English", "Geography", "Computer");

        Button showScoresButton = new Button("显示成绩");
        ScrollPane scrollPane = new ScrollPane();
        scoresBox = new VBox(10);
        scrollPane.setContent(scoresBox);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        showScoresButton.setOnAction(e -> {
            String subject = subjectComboBox.getValue();
            if (isTeacher) {
                showSubjectScores(subject);
            } else {
                showStudentScores(username, subject);
            }
        });

        Button backButton = new Button("返回");
        backButton.setOnAction(e -> {
            if (isTeacher) {
                primaryStage.setScene(new Scene(new view.ProfessorView(primaryStage, questionController, userController), 900, 600));
            } else {
                primaryStage.setScene(new Scene(new view.StudentView(primaryStage, username, questionController, userController), 900, 600));
            }
        });

        BarChart<String, Number> barChart = createBarChart();
        PieChart pieChart = createPieChart();
        Button analyzeButton = new Button("分析成绩");
        analyzeButton.setOnAction(e -> analyzeScores(pieChart, barChart, username));
        getChildren().addAll(subjectLabel, subjectComboBox, showScoresButton, scrollPane, backButton, analyzeButton, pieChart, barChart);
    }

    private void showStudentScores(String username, String subject) {
        List<Exam> exams = examController.getExamsByStudentAndSubject(username, subject);
        scoresBox.getChildren().clear();

        if (exams.isEmpty()) {
            Label noScoresLabel = new Label("没有该科目的成绩记录。");
            scoresBox.getChildren().add(noScoresLabel);
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (Exam exam : exams) {
                exam.recalculateScore();
                String formattedDateTime = exam.getTime().format(formatter);
                Label scoreLabel = new Label("科目: " + exam.getSubject() + " 成绩: " + exam.getScore() + " 时间: " + formattedDateTime);
                scoresBox.getChildren().add(scoreLabel);
            }
        }
    }

    private void showSubjectScores(String subject) {
        List<Exam> exams = examController.getExamsBySubject(subject);
        scoresBox.getChildren().clear();

        if (exams.isEmpty()) {
            Label noScoresLabel = new Label("没有该科目的成绩记录。");
            scoresBox.getChildren().add(noScoresLabel);
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (Exam exam : exams) {
                exam.recalculateScore();
                String formattedDateTime = exam.getTime().format(formatter);
                Label scoreLabel = new Label("学生: " + exam.getStudentUsername() + " 成绩: " + exam.getScore() + " 时间: " + formattedDateTime);
                scoresBox.getChildren().add(scoreLabel);
            }
        }
    }

    private BarChart<String, Number> createBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("科目");
        xAxis.setCategories(FXCollections.observableArrayList(
                "Math", "Science", "History", "English", "Geography", "Computer"
        ));

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("平均成绩");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("各科目平均成绩");
        return barChart;
    }

    private PieChart createPieChart() {
        PieChart pieChart = new PieChart();
        pieChart.setTitle("各科目成绩分析");

        return pieChart;
    }

    private void analyzeScores(PieChart pieChart, BarChart<String, Number> barChart, String username) {
        List<Exam> allExams = isTeacher ? examController.getAllExams() : examController.getExamsByStudent(username);
        Map<String, Integer> subjectCount = new HashMap<>();
        Map<String, Integer> scoreSum = new HashMap<>();

        for (Exam exam : allExams) {
            String subject = exam.getSubject();
            int score = exam.getScore();
            subjectCount.put(subject, subjectCount.getOrDefault(subject, 0) + 1);
            scoreSum.put(subject, scoreSum.getOrDefault(subject, 0) + score);
        }

        pieChart.getData().clear();
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        DecimalFormat df = new DecimalFormat("#.##");
        df.setMaximumFractionDigits(2); // Set the maximum fraction digits to 2
        for (String subject : subjectCount.keySet()) {
            int count = subjectCount.get(subject);
            int totalScore = scoreSum.get(subject);
            double avgScore = (double) totalScore / count;
            PieChart.Data slice = new PieChart.Data(subject + " (平均成绩: " + df.format(avgScore) + ")", count);
            pieChart.getData().add(slice);
            series.getData().add(new XYChart.Data<>(subject, avgScore));
        }

        barChart.getData().add(series);
    }
}
