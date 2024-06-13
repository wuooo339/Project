package dao;

import model.Exam;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class ExamDao {
    private static final String FILE_PATH = "src/main/resources/exams.dat";
    private List<Exam> exams;

    public ExamDao() {
        exams = loadExams();
    }

    public List<Exam> getAllExams() {
        return exams;
    }

    public boolean deleteExam(String examId) {
        boolean result = exams.removeIf(e -> e.getId().equals(examId));
        saveExams();
        return result;
    }

    // 添加考试
    public boolean addExam(Exam exam) {
        boolean result = exams.add(exam);
        saveExams();
        return result;
    }

    // 根据学生获取考试列表
    public List<Exam> getExamsByStudent(String student) {
        return exams.stream()
                .filter(e -> e.getStudent().equalsIgnoreCase(student))
                .collect(Collectors.toList());
    }
    public List<Exam> findExamsByStudentAndSubject(String username, String subject) {
        return exams.stream()
                .filter(exam -> exam.getStudent().equals(username) && exam.getSubject().equals(subject))
                .collect(Collectors.toList());
    }

    // Public method to load exams
    public List<Exam> loadExams() {
        if (Files.notExists(Paths.get(FILE_PATH))) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(FILE_PATH)))) {
            return (List<Exam>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void saveExams() {
        ensureDirectoryExists(FILE_PATH);
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(FILE_PATH)))) {
            oos.writeObject(exams);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ensureDirectoryExists(String filePath) {
        File file = new File(filePath);
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
