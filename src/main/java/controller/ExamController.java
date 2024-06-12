package controller;

import model.Exam;
import service.ExamService;
import java.util.List;
import model.Question;
public class ExamController {
    private ExamService examService = new ExamService();

    // Add exam
    public void addExam(String student, List<Question> questions, List<String> answers) {
        examService.addExam(student, questions, answers);
    }

    // Get exams by student
    public List<Exam> getExamsByStudent(String student) {
        return examService.getExamsByStudent(student);
    }

    // Load exams
    public List<Exam> loadExams() {
        return examService.loadExams();
    }

    public List<Exam> getAllExams() {
        return examService.getAllExams();
    }

    public void deleteExam(String examId) {
        examService.deleteExam(examId);
    }
}
