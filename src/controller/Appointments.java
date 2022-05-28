package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Appointments {

    Stage stage;
    Parent scene;

    @FXML private TableView<?> appointmentsTable;
    @FXML private TableColumn<?, ?> apptsColumnContact;
    @FXML private TableColumn<?, ?> apptsColumnCustomerID;
    @FXML private TableColumn<?, ?> apptsColumnDescription;
    @FXML private TableColumn<?, ?> apptsColumnEndDate;
    @FXML private TableColumn<?, ?> apptsColumnID;
    @FXML private TableColumn<?, ?> apptsColumnLocation;
    @FXML private TableColumn<?, ?> apptsColumnStartDate;
    @FXML private TableColumn<?, ?> apptsColumnTitle;
    @FXML private TableColumn<?, ?> apptsColumnType;
    @FXML private TableColumn<?, ?> apptsColumnUserID;
    @FXML private RadioButton radioButtonAll;
    @FXML private RadioButton radioButtonMonth;
    @FXML private RadioButton radioButtonWeek;
    @FXML private ToggleGroup toggleGroup;

    @FXML void onBackButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
        stage.setTitle("Home");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML void onAddAppointmentButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsAdd.fxml"));
        stage.setTitle("Add Appointment");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML void onModifyAppointmentButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsModify.fxml"));
        stage.setTitle("Modify Appointment");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML void onDeleteAppointmentButton(ActionEvent event) {

    }


    @FXML void onRadioButtonAll(ActionEvent event) {

    }

    @FXML void onRadioButtonMonth(ActionEvent event) {

    }

    @FXML void onRadioButtonWeek(ActionEvent event) {

    }

}
