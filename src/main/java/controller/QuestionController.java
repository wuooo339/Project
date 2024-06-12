package controller;

import model.Question;
import service.QuestionService;

import java.io.IOException;
import java.util.List;

public class QuestionController {
    private final QuestionService questionService = new QuestionService();

    public void addQuestion(Question question) {
        questionService.addQuestion(question);
    }

    public List<Question> getQuestionsBySubject(String subject) {
        return questionService.getQuestionsBySubject(subject);
    }

    public void importQuestionsFromCSV(String filePath) throws IOException {
        questionService.importQuestionsFromCSV(filePath);
    }

    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    public void deleteQuestion(String questionId) {
        questionService.deleteQuestion(questionId);
    }
}
