package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;


public class Login implements Initializable {

    public Label loginLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // System.out.println("I am initialized");
        loginLabel.setText("I am the label!");
    }

    public void onSubmitButtonAction(ActionEvent actionEvent) {
        // System.out.println("This button was clicked");
        loginLabel.setText("This button was clicked");
    }

}
