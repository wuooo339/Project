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
                String correctAnswer = csvRecord.get("correctAnswer");
                Question question = new Question(subject, type, questionText, options, correctAnswer);
                questionDao.save(question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Question> getQuestionsBySubject(String subject) {
        return questionDao.findBySubject(subject);
    }

    public void addQuestion(Question question) {
        questionDao.save(question);
    }
}
