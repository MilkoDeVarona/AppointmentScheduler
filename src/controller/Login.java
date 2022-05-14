package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;


public class Login implements Initializable {

    public Label LoginLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // System.out.println("I am initialized");
        LoginLabel.setText("I am the label!");
    }

    public void onSubmitButtonAction(ActionEvent actionEvent) {
        // System.out.println("This button was clicked");
        // LoginLabel.setText("Click clickity click");
    }

}
