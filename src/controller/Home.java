package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Home {
    Stage stage;
    Parent scene;

    @FXML public void onCustomersButton(ActionEvent actionEvent) {
        System.out.println("View customers button clicked!");
    }

    @FXML public void onAppointmentsButton(ActionEvent actionEvent) {
        System.out.println("View appointments button clicked!");
    }

    @FXML public void onReportsButton(ActionEvent actionEvent) {
        System.out.println("View reports button clicked!");
    }

    @FXML public void onLogOutButton(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(scene));
        stage.show();
    }

}
