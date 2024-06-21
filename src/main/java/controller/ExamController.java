package controller;

import model.Exam;
import service.ExamService;
import java.util.List;
public class ExamController {
    private final ExamService examService = new ExamService();

    // Add exam
    public void addExam(Exam exam) {
        examService.addExam(exam);
    }

    // Get exams by student
    public List<Exam> getExamsByStudent(String student) {
        return examService.getExamsByStudent(student);
    }

    // Load exams
    public List<Exam> loadExams() {
        return examService.loadExams();
    }
    public List<Exam> getExamsByStudentAndSubject(String username, String subject) {
        return examService.getExamsByStudentAndSubject(username, subject);
    }
    public List<Exam> getExamsBySubject(String subject) {
        return examService.getExamsBySubject(subject);
    }

    public List<Exam> getAllExams() {
        return examService.getAllExams();
    }

    public void deleteExam(String examId) {
        examService.deleteExam(examId);
    }
}
