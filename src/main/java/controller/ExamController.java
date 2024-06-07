package controller;

import service.ExamService;
import model.Exam;
import model.Question;

import java.util.List;

public class ExamController {
    private ExamService examService = new ExamService();

    public Exam createExam(String student, List<Question> questions, List<String> answers) {
        return examService.createExam(student, questions, answers);
    }

    public List<Exam> getExamsByStudent(String student) {
        return examService.getExamsByStudent(student);
    }
}
