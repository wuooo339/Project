package dao;

import model.Question;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Question;
public class QuestionDao {
    private static final String FILE_PATH = "src/main/resources/questions.dat";
    private List<Question> questions;

    public QuestionDao() {
        questions = loadQuestions();
    }

    public List<Question> getAllQuestions() {
        return questions;
    }

    public boolean deleteQuestion(String questionId) {
        boolean result = questions.removeIf(q -> q.getId().equals(questionId));
        saveQuestions();
        return result;
    }

    public boolean addQuestion(Question question) {
        boolean result = questions.add(question);
        saveQuestions();
        return result;
    }

    public List<Question> getQuestionsBySubject(String subject) {
        return questions.stream()
                .filter(q -> q.getSubject().equalsIgnoreCase(subject))
                .collect(Collectors.toList());
    }

    public List<Question> findBySubject(String subject) {
        return questions.stream()
                .filter(q -> q.getSubject().equalsIgnoreCase(subject))
                .collect(Collectors.toList());
    }

    public boolean save(Question question) {
        return addQuestion(question);
    }

    private void saveQuestions() {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(FILE_PATH)))) {
            oos.writeObject(questions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Question> loadQuestions() {
        if (Files.notExists(Paths.get(FILE_PATH))) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(FILE_PATH)))) {
            return (List<Question>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Question> findBySubjectAndDifficulty(String subject, int difficulty, int limit) {
        return questions.stream()
                .filter(question -> question.getSubject().equals(subject) && question.getDifficulty() == difficulty)
                .limit(limit)
                .collect(Collectors.toList());
    }

}
