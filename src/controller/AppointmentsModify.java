package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AppointmentsModify {

    Stage stage;
    Parent scene;

    @FXML private TextField addAppointmentDescription;
    @FXML private TextField addAppointmentLocation;
    @FXML private ComboBox<?> modAppointmentContact;
    @FXML private ComboBox<?> modAppointmentCustID;
    @FXML private DatePicker modAppointmentDate;
    @FXML private ComboBox<?> modAppointmentEnd;
    @FXML private TextField modAppointmentID;
    @FXML private ComboBox<?> modAppointmentStart;
    @FXML private TextField modAppointmentTitle;
    @FXML private ComboBox<?> modAppointmentType;
    @FXML private ComboBox<?> modAppointmentUserID;

    @FXML void onCancelButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setTitle("Appointments");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML void onSaveButton(ActionEvent event) {
    }

}
