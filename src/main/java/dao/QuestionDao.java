package dao;

import model.Question;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;

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
        // 先过滤出符合科目和难度的题目
        List<Question> filteredQuestions = questions.stream()
                .filter(question -> question.getSubject().equals(subject) && question.getDifficulty() == difficulty)
                .collect(Collectors.toList());

        // 如果符合条件的题目数量小于限制数量,直接返回
        if (filteredQuestions.size() <= limit) {
            return filteredQuestions;
        }
        // 创建一个随机数生成器
        Random random = new Random();
        // 创建一个列表用于存储随机选取的题目
        List<Question> randomQuestions = new ArrayList<>();
        // 随机选取题目,直到达到限制数量
        while (randomQuestions.size() < limit) {
            int randomIndex = random.nextInt(filteredQuestions.size());
            Question randomQuestion = filteredQuestions.get(randomIndex);
            if (!randomQuestions.contains(randomQuestion)) {
                randomQuestions.add(randomQuestion);
            }
        }

        return randomQuestions;
    }

}
