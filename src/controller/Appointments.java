package controller;
import database.DAOAppointments;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Appointments controller class.
 */
public class Appointments implements Initializable {
    Stage stage;
    Parent scene;

    @FXML private TableView<model.Appointments> appointmentsTable;
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

    /**
     * Method sends user back to home screen.
     * @param event
     * @throws IOException
     */
    @FXML void onBackButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
        stage.setTitle("Home");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method sends user to Add Appointment screen.
     * @param event
     * @throws IOException
     */
    @FXML void onAddAppointmentButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsAdd.fxml"));
        stage.setTitle("Add Appointment");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method sends user to modify a selected appointment.
     * @param event
     * @throws IOException
     */
    @FXML void onModifyAppointmentButton(ActionEvent event) throws IOException {
        if (appointmentsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select an appointment to modify");
            alert.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/AppointmentsModify.fxml"));
            loader.load();

            AppointmentsModify amc = loader.getController();
            amc.modSelectedAppointment(appointmentsTable.getSelectionModel().getSelectedItem());
            if (appointmentsTable.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Please select an appointment to modify");
                alert.showAndWait();
            } else {
                stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setTitle("Modify Appointment");
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }

    /**
     * Method deletes selected appointment.
     * @param event
     * @throws SQLException
     */
    @FXML void onDeleteAppointmentButton(ActionEvent event) throws SQLException {
        model.Appointments selected = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select an appointment to delete");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected appointment, do you want to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                model.Appointments a = appointmentsTable.getSelectionModel().getSelectedItem();
                DAOAppointments.deleteAppointment(a.getAppointmentID());

                appointmentsTable.setItems(DAOAppointments.viewAllAppointments());
                appointmentsTable.refresh();

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Appointment successfully deleted");
                alert2.setContentText("Appointment " + selected.getAppointmentID() + " of type " + selected.getType() + " has been deleted");
                alert2.showAndWait();
            }
        }
    }

    /**
     * Method sets appointments table and toggles view depending on radio button selected.
     * @param event
     * @throws SQLException
     */
    @FXML
    public void onViewAppointments(ActionEvent event) throws SQLException {
        if (radioButtonAll.isSelected()) {
            appointmentsTable.setItems(DAOAppointments.viewAllAppointments());
        } else if (radioButtonMonth.isSelected()) {
            appointmentsTable.setItems(DAOAppointments.viewAppointmentsByMonth());
        } else if (radioButtonWeek.isSelected()) {
            appointmentsTable.setItems(DAOAppointments.viewAppointmentsByWeek());
        }
    }

    /**
     * Methods initializes and populates appointments table.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentsTable.setItems(DAOAppointments.viewAllAppointments());
            apptsColumnID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            apptsColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
            apptsColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            apptsColumnUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
            apptsColumnEndDate.setCellValueFactory(new PropertyValueFactory<>("end"));
            apptsColumnContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            apptsColumnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
            apptsColumnStartDate.setCellValueFactory(new PropertyValueFactory<>("start"));
            apptsColumnCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            apptsColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
