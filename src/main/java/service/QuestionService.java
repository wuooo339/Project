package service;

import dao.QuestionDao;
import model.Question;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionService {
    private final QuestionDao questionDao = new QuestionDao();
    private static final Logger LOGGER = Logger.getLogger(QuestionService.class.getName());

    public void importQuestionsFromCSV(String filePath) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(filePath)), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                String subject = csvRecord.get("subject").trim();
                String type = csvRecord.get("type").trim();
                String questionText = csvRecord.get("questionText").trim();
                String options = csvRecord.get("options").trim();
                String correctAnswer = csvRecord.get("correctAnswer").trim().replace("\" ", " ");
                int difficulty;
                try {
                    difficulty = Integer.parseInt(csvRecord.get("difficulty").trim());
                } catch (NumberFormatException e) {
                    // If difficulty is not an integer, handle the error appropriately
                    LOGGER.log(Level.WARNING, "Invalid difficulty value for record: " + csvRecord, e);
                    continue; // Skip this record and move to the next
                }
                Question question = new Question(subject, type, questionText, options, correctAnswer, difficulty);
                questionDao.save(question);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to read CSV file: " + filePath, e);
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
