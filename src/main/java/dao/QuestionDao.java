package dao;

import model.Question;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionDao {
    private static final String FILE_PATH = "src/main/resources/questions.dat";
    private List<Question> questions;

    public QuestionDao() {
        questions = loadQuestions();
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
}
