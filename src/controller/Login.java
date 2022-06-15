package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {
    Stage stage;
    Parent scene;

    @FXML private Label loginLabel;
    @FXML private Label loginErrorLabel;
    @FXML private PasswordField passwordTextField;
    @FXML private TextField userNameTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginLabel.setText("Set location here");
        passwordTextField.setFocusTraversable(false);
    }

    @FXML
    public void onSubmitButtonAction(ActionEvent actionEvent) throws IOException {
        if (userNameTextField.getText().isBlank() && passwordTextField.getText().isBlank()) {
            loginErrorLabel.setText("Please, enter a username and password to login!");
        } else if (userNameTextField.getText().isBlank()) {
            loginErrorLabel.setText("Please, enter a username to login!");
        } else if (passwordTextField.getText().isBlank()) {
            loginErrorLabel.setText("Please, enter a password to login!");
        } else {
            stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
            stage.setTitle("Home");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

}
