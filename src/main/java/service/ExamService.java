package service;

import dao.ExamDao;
import model.Exam;
import model.Question;
import java.util.List;

public class ExamService {
    private ExamDao examDao = new ExamDao();
    // 添加考试
    public void addExam(String student, List<Question> questions, List<String> answers) {
        examDao.addExam(new Exam(student, questions, answers));
    }
    // 根据学生获取考试列表
    public List<Exam> getExamsByStudent(String student) {
        return examDao.getExamsByStudent(student);
    }

    // 创建考试并返回新创建的考试对象
    public Exam createExam(String student, List<Question> questions, List<String> answers) {
        Exam exam = new Exam(student, questions, answers);
        examDao.addExam(exam);
        return exam;
    }
}
