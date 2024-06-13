package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controller.*;
import model.User;

public class ManageUsersView extends VBox {
    private UserController userController;
    private ListView<User> userListView;

    public ManageUsersView(Stage primaryStage, UserController userController) {
        this.userController = userController;
        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(10);

        userListView = new ListView<>();
        userListView.getItems().addAll(userController.getAllUsers());

        Button deleteUserButton = new Button("删除用户");
        deleteUserButton.setOnAction(e -> {
            User selectedUser = userListView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                userController.deleteUser(selectedUser.getUsername());
                userListView.getItems().remove(selectedUser);
            }
        });
        Button backButton = new Button("返回");
        backButton.setOnAction(e -> primaryStage.setScene(new Scene(new view.AdminView(primaryStage, userController, new ExamController(), new QuestionController()), 600, 400)));

        getChildren().addAll(userListView, deleteUserButton, backButton);
    }
}
