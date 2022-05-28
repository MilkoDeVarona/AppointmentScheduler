package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomersModify {

    Stage stage;
    Parent scene;

    @FXML private TextField addName;
    @FXML private TextField modAddress;
    @FXML private TextField modCustomerID;
    @FXML private TextField modPhone;
    @FXML private TextField modPostalCode;
    @FXML private ComboBox<?> modCountryCB;
    @FXML private ComboBox<?> modDivisionCB;

    @FXML void onCancelButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        stage.setTitle("Customers");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML void onSaveButton(ActionEvent event) {
    }

}
