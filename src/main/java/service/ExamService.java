package service;

import dao.ExamDao;
import model.Exam;
import model.Question;

import java.util.List;

public class ExamService {
    private ExamDao examDao = new ExamDao();
    // Add exam
    public void addExam(String student, List<Question> questions, List<String> answers) {
        examDao.addExam(new Exam(student, questions, answers));
    }
    // Get exams by student
    public List<Exam> getExamsByStudent(String student) {
        return examDao.getExamsByStudent(student);
    }
    // Load exams
    public List<Exam> loadExams() {
        return examDao.loadExams();
    }
}
