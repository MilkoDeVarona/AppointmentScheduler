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

public class AppointmentsAdd {

    Stage stage;
    Parent scene;

    @FXML private ComboBox<?> addAppointmentContact;
    @FXML private ComboBox<?> addAppointmentCustID;
    @FXML private DatePicker addAppointmentDate;
    @FXML private TextField addAppointmentDescription;
    @FXML private ComboBox<?> addAppointmentEnd;
    @FXML private TextField addAppointmentID;
    @FXML private TextField addAppointmentLocation;
    @FXML private ComboBox<?> addAppointmentStart;
    @FXML private TextField addAppointmentTitle;
    @FXML private ComboBox<?> addAppointmentType;
    @FXML private ComboBox<?> addAppointmentUserID;

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
