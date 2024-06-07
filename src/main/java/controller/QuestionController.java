package controller;

import service.QuestionService;
import model.Question;

import java.util.List;

public class QuestionController {
    private QuestionService questionService = new QuestionService();

    public boolean addQuestion(Question question) {
        return questionService.addQuestion(question);
    }

    public List<Question> getQuestionsBySubject(String subject) {
        return questionService.getQuestionsBySubject(subject);
    }
}
