import controller.UserController;
import controller.QuestionController;
import controller.ExamController;
import model.Question;
import model.User;
import model.Exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static UserController userController = new UserController();
    private static QuestionController questionController = new QuestionController();
    private static ExamController examController = new ExamController();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. 注册");
            System.out.println("2. 登录");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            if (choice == 1) {
                register();
            } else if (choice == 2) {
                login();
            }
        }
    }

    private static void register() {
        System.out.println("请输入用户名:");
        String username = scanner.nextLine();
        System.out.println("请输入密码:");
        String password = scanner.nextLine();
        System.out.println("请输入角色(student/professor):");
        String role = scanner.nextLine();
        boolean success = userController.register(username, password, role);
        if (success) {
            System.out.println("注册成功!");
        } else {
            System.out.println("用户名已存在!");
        }
    }

    private static void login() {
        System.out.println("请输入用户名:");
        String username = scanner.nextLine();
        System.out.println("请输入密码:");
        String password = scanner.nextLine();
        User user = userController.login(username, password);
        if (user != null) {
            System.out.println("登录成功!");
            if (user.getRole().equals("student")) {
                studentMenu(username);
            } else if (user.getRole().equals("professor")) {
                professorMenu();
            }
        } else {
            System.out.println("用户名或密码错误!");
        }
    }

    private static void studentMenu(String username) {
        while (true) {
            System.out.println("1. 选择科目并答题");
            System.out.println("2. 查看成绩");
            System.out.println("3. 退出");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            if (choice == 1) {
                answerQuestions(username);
            } else if (choice == 2) {
                viewScores(username);
            } else if (choice == 3) {
                break;
            }
        }
    }

    private static void professorMenu() {
        while (true) {
            System.out.println("1. 录入题目");
            System.out.println("2. 从CSV文件批量导入题目");
            System.out.println("3. 退出");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            if (choice == 1) {
                addQuestion();
            } else if (choice == 2) {
                importQuestionsFromCSV();
            } else if (choice == 3) {
                break;
            }
        }
    }

    private static void addQuestion() {
        System.out.println("请输入题目类型(choice/blank):");
        String type = scanner.nextLine();
        System.out.println("请输入科目:");
        String subject = scanner.nextLine();
        System.out.println("请输入题目:");
        String questionText = scanner.nextLine();

        Question question = new Question(subject, type, questionText, null, null);

        if (type.equals("choice")) {
            List<String> choices = new ArrayList<>();
            System.out.println("请输入选项 (输入'END'结束):");
            while (true) {
                String choice = scanner.nextLine();
                if (choice.equals("END")) {
                    break;
                }
                choices.add(choice);
            }
            String options = String.join("|", choices);
            question.setOptions(options);

            System.out.println("请输入正确答案:");
            String correctAnswer = scanner.nextLine();
            question.setCorrectAnswer(correctAnswer);
        } else if (type.equals("blank")) {
            System.out.println("请输入正确答案:");
            String correctAnswer = scanner.nextLine();
            question.setCorrectAnswer(correctAnswer);
        }

        questionController.addQuestion(question);
    }

    private static void importQuestionsFromCSV() {
        System.out.println("请输入CSV文件路径:");
        String filePath = scanner.nextLine();
        try {
            questionController.importQuestionsFromCSV(filePath);
            System.out.println("题目导入成功!");
        } catch (Exception e) {
            System.out.println("导入失败: " + e.getMessage());
        }
    }

    private static void answerQuestions(String username) {
        System.out.println("请输入科目:");
        String subject = scanner.nextLine();
        List<Question> questions = questionController.getQuestionsBySubject(subject);
        List<String> answers = new ArrayList<>();
        for (Question question : questions) {
            System.out.println(question.getQuestionText());
            if (question.getChoices() != null) {
                for (int i = 0; i < question.getChoices().size(); i++) {
                    System.out.println((i + 1) + ". " + question.getChoices().get(i));
                }
            }
            String answer = scanner.nextLine();
            answers.add(answer);
        }
        examController.createExam(username, questions, answers);
    }

    private static void viewScores(String username) {
        List<Exam> exams = examController.getExamsByStudent(username);
        for (Exam exam : exams) {
            System.out.println("科目: " + exam.getSubject() + " 成绩: " + exam.getScore());
        }
    }
}
