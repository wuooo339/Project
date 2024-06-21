package service;

import dao.*;
import model.*;
import java.util.List;
import java.util.stream.Collectors;

public class ExamService {
    private final ExamDao examDao = new ExamDao();
    // Add exam
    public void addExam(Exam exam) {
        examDao.addExam(exam);
    }
    // Get exams by student
    public List<Exam> getExamsByStudent(String student) {
        return examDao.getExamsByStudent(student);
    }
    // Load exams
    public List<Exam> loadExams() {
        return examDao.loadExams();
    }
    public List<Exam> getAllExams() {
        return examDao.getAllExams();
    }
    public List<Exam> getExamsByStudentAndSubject(String username, String subject) {
        return examDao.findExamsByStudentAndSubject(username, subject);
    }

    public void deleteExam(String examId) {
        examDao.deleteExam(examId);
    }
    public List<Exam> getExamsBySubject(String subject) {
        List<Exam> allExams = examDao.getAllExams();
        return allExams.stream()
                .filter(exam -> exam.getSubject().equals(subject))
                .collect(Collectors.toList());
    }
}
