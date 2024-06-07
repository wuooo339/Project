package service;

import dao.QuestionDao;
import model.Question;

import java.util.List;

public class QuestionService {
    private QuestionDao questionDao = new QuestionDao();

    public boolean addQuestion(Question question) {
        return questionDao.save(question);
    }

    public List<Question> getQuestionsBySubject(String subject) {
        return questionDao.findBySubject(subject);
    }
}
