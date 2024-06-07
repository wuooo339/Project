package dao;

import model.Exam;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExamDao {
    private List<Exam> exams = new ArrayList<>();

    // 添加考试
    public boolean addExam(Exam exam) {
        return exams.add(exam);
    }

    // 根据学生获取考试列表
    public List<Exam> getExamsByStudent(String student) {
        return exams.stream()
                .filter(e -> e.getStudent().equalsIgnoreCase(student))
                .collect(Collectors.toList());
    }
}
