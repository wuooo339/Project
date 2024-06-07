package dao;
import java.util.stream.Collectors;
import model.Question;
import java.util.ArrayList;
import java.util.List;

public class QuestionDao {
    private List<Question> questions = new ArrayList<>();

    // 添加题目
    public boolean addQuestion(Question question) {
        return questions.add(question);
    }

    // 根据科目获取题目列表
    public List<Question> getQuestionsBySubject(String subject) {
        return questions.stream()
                .filter(q -> q.getSubject().equalsIgnoreCase(subject))
                .collect(Collectors.toList());
    }

    // 根据科目查找题目
    public List<Question> findBySubject(String subject) {
        return questions.stream()
                .filter(q -> q.getSubject().equalsIgnoreCase(subject))
                .collect(Collectors.toList());
    }

    // 保存题目
    public boolean save(Question question) {
        addQuestion(question);
        return false;
    }
}
