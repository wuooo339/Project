package service;

import dao.QuestionDao;
import model.Question;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class QuestionService {
    private QuestionDao questionDao = new QuestionDao();

    public void importQuestionsFromCSV(String filePath) {
        try (FileReader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                String subject = csvRecord.get("subject");
                String type = csvRecord.get("type");
                String questionText = csvRecord.get("questionText");
                String options = csvRecord.get("options");
                String correctAnswer = csvRecord.get("correctAnswer").trim();
                int difficulty;

                try {
                    difficulty = Integer.parseInt(csvRecord.get("difficulty").trim());
                } catch (NumberFormatException e) {
                    // If difficulty is not an integer, handle the error appropriately
                    e.printStackTrace();
                    continue; // Skip this record and move to the next
                }

                Question question = new Question(subject, type, questionText, options, correctAnswer, difficulty);
                questionDao.save(question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Question> getQuestionsBySubject(String subject) {
        return questionDao.findBySubject(subject);
    }

    public List<Question> getQuestionsBySubjectAndDifficulty(String subject, int difficulty, int limit) {
        return questionDao.findBySubjectAndDifficulty(subject, difficulty, limit);
    }

    public void addQuestion(Question question) {
        questionDao.save(question);
    }
    public List<Question> getAllQuestions() {
        return questionDao.getAllQuestions();
    }

    public void deleteQuestion(String questionId) {
        questionDao.deleteQuestion(questionId);
    }
}
