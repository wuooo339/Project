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
    private final QuestionDao questionDao = new QuestionDao();

    // 添加题目
    public void addQuestion(Question question) {
        questionDao.addQuestion(question);
    }

    // 根据科目获取题目列表
    public List<Question> getQuestionsBySubject(String subject) {
        return questionDao.getQuestionsBySubject(subject);
    }

    // 从CSV文件批量导入题目
    public void importQuestionsFromCSV(String filePath) throws IOException {
        try (FileReader fileReader = new FileReader(filePath);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader)) {

            for (CSVRecord csvRecord : csvParser) {
                String subject = csvRecord.get("subject");
                String type = csvRecord.get("type");
                String questionText = csvRecord.get("questionText");
                String options = csvRecord.get("options");
                String correctAnswer = csvRecord.get("correctAnswer");
                Question question = new Question(subject, type, questionText, options, correctAnswer);
                System.out.println("record:"+subject);
                addQuestion(question);
            }
        }
    }
}
