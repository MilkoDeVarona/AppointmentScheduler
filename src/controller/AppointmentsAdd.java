package controller;
import database.DAOAppointments;
import database.DAOContacts;
import database.DAOCustomers;
import database.DAOUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Add appointments controller class.
 */
public class AppointmentsAdd implements Initializable {
    Stage stage;
    Parent scene;

    @FXML private DatePicker addAppointmentDate;
    @FXML private TextField addAppointmentDescription;
    @FXML private TextField addAppointmentID;
    @FXML private TextField addAppointmentLocation;
    @FXML private TextField addAppointmentTitle;
    @FXML private ComboBox<LocalTime> addAppointmentStart;
    @FXML private ComboBox<LocalTime> addAppointmentEnd;
    @FXML private ComboBox<String> addAppointmentType;
    @FXML private ComboBox<Users> addAppointmentUserID;
    @FXML private ComboBox<Contacts> addAppointmentContact;
    @FXML private ComboBox<Customers> addAppointmentCustID;

    /**
     * Method populates Contact combo box.
     * @throws SQLException
     */
    private void populateContactCB() throws SQLException {
        addAppointmentContact.setItems(DAOContacts.getAllContacts());
    }

    /**
     * Method creates list of appointment types to populate Type combo box.
     */
    private void populateTypeCB() {
        ObservableList<String> typeList = FXCollections.observableArrayList();
        typeList.addAll("Planning Session", "De-Briefing", "Informal", "Phone Call", "Priority", "Brainstorm");
        addAppointmentType.setItems(typeList);
    }

    /**
     * Method combines selected date and start time into a start LocalDateTime object.
     * @return
     */
    public LocalDateTime startDateTime() {
        LocalTime startTime = addAppointmentStart.getSelectionModel().getSelectedItem();
        LocalDate appointmentDateTime = addAppointmentDate.getValue();
        //LocalDateTime sdt = LocalDateTime.of(appointmentDateTime, startTime);
        return LocalDateTime.of(appointmentDateTime, startTime);
    }

    /**
     * Method combines selected date and end time into an end LocalDateTime object.
     * @return
     */
    public LocalDateTime endDateTime() {
        LocalTime endTime = addAppointmentEnd.getSelectionModel().getSelectedItem();
        LocalDate appointmentDateTime = addAppointmentDate.getValue();
        //LocalDateTime edt = LocalDateTime.of(appointmentDateTime, endTime);
        return LocalDateTime.of(appointmentDateTime, endTime);
    }

    /**
     * Method creates business hours to populate start and end appointment hours combo boxes.
     * @return
     */
    public ObservableList<LocalTime> hoursOfBusiness() {

        ObservableList<LocalTime> hoursList = FXCollections.observableArrayList();
        LocalTime openHoursEST = LocalTime.of(8, 0);
        LocalTime closeHoursEST = LocalTime.of(22, 0);

        ZoneId timeEST = ZoneId.of("America/New_York");
        ZoneId timeLocal = ZoneId.systemDefault();

        ZonedDateTime openBusinessEST = ZonedDateTime.of(LocalDate.now(), openHoursEST, timeEST);
        ZonedDateTime closeBusinessEST = ZonedDateTime.of(LocalDate.now(), closeHoursEST, timeEST);
        ZonedDateTime openBusinessLocal = openBusinessEST.withZoneSameInstant(timeLocal);
        ZonedDateTime closeBusinessLocal = closeBusinessEST.withZoneSameInstant(timeLocal);

        ZonedDateTime tms = openBusinessLocal.minusMinutes(15);
        while (tms.isBefore(closeBusinessLocal)) {
            tms = tms.plusMinutes(15);
            hoursList.add(LocalTime.from(tms));
            if ((tms.equals(closeBusinessLocal) || tms.isAfter(closeBusinessLocal))) {
                break;
            }
        }
        return hoursList;
    }

    /**
     * Populates Start and End Time combo boxes in 15 minute increments.
     */
    private void populateTimeCB() {
        addAppointmentStart.setItems(hoursOfBusiness());
        addAppointmentEnd.setItems(hoursOfBusiness());
    }

    /**
     * Populates Customer ID combo box.
     * @throws SQLException
     */
    private void populateCustomerIDCB() throws SQLException {
        addAppointmentCustID.setItems(DAOCustomers.getAllCustomers());
    }

    /**
     * Populates User ID combo box.
     * @throws SQLException
     */
    private void populateUserIDCB() throws SQLException {
        addAppointmentUserID.setItems(DAOUsers.getAllUsers());
    }

    /**
     * Method checks for empty fields and overlapping appointments when creating an appointment.
     * @param appts
     * @return
     * @throws SQLException
     */
    public boolean alerts (String appts) throws SQLException {

        // Checks for empty fields
        if (addAppointmentTitle.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Title field can not be empty");
            alert.showAndWait();
            return false;
        }

        if (addAppointmentDescription.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Description field can not be empty");
            alert.showAndWait();
            return false;
        }

        if (addAppointmentLocation.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Location field can not be empty");
            alert.showAndWait();
            return false;
        }

        if (addAppointmentContact.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please select a contact");
            alert.showAndWait();
            return false;
        }

        if (addAppointmentType.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please select an appointment type");
            alert.showAndWait();
            return false;
        }

        if (addAppointmentDate.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please select an appointment date");
            alert.showAndWait();
            return false;
        }

        if (addAppointmentStart.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please select an appointment start time");
            alert.showAndWait();
            return false;
        }

        if (addAppointmentEnd.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please select an appointment end time");
            alert.showAndWait();
            return false;
        }

        if (addAppointmentCustID.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please select a customer");
            alert.showAndWait();
            return false;
        }

        if (addAppointmentUserID.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please select a user");
            alert.showAndWait();
            return false;
        }

        if (startDateTime().isAfter(endDateTime()) || startDateTime().isEqual(endDateTime())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Start time can not be equal or after end time.");
            alert.showAndWait();
            return false;
        }

        // Checks for overlapping appointments
        ObservableList<Appointments> appointmentsList = DAOAppointments.viewAllAppointments();
        for (Appointments a : appointmentsList) {
            if (a.getCustomerID() != addAppointmentCustID.getValue().getCustomerID()) {
               continue;
            }
            LocalDateTime existingStart = a.getStart();
            LocalDateTime newStart = startDateTime();
            LocalDateTime existingEnd = a.getEnd();
            LocalDateTime newEnd = endDateTime();

            if ((existingStart.isAfter(newStart) && existingStart.isBefore(newEnd)) || existingStart.isEqual(newStart)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("New appointment overlaps with existing Appointment. Select new time");
                alert.showAndWait();
                return false;
            }

            if ((existingEnd.isAfter(newStart) && existingEnd.isBefore(newEnd)) || existingEnd.isEqual(newEnd)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("New appointment overlaps with existing Appointment. Select new time");
                alert.showAndWait();
                return false;
            }

            if (existingStart.isBefore(newStart) && existingEnd.isAfter(newEnd)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("New appointment overlaps with existing Appointment. Select new time");
                alert.showAndWait();
                return false;
            }
        }
        return true;
    }

    /**
     * Method cancels add appointment action and sends user back to Appointments screen.
     * @param event
     * @throws IOException
     */
    @FXML void onCancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
            stage.setTitle("Appointments");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Method saves a new appointment.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML void onSaveButton(ActionEvent event) throws SQLException, IOException{
        boolean valid = alerts(addAppointmentID.getText());
        if (valid) {
            String title = addAppointmentTitle.getText();
            String description = addAppointmentDescription.getText();
            String location = addAppointmentLocation.getText();
            String type = addAppointmentType.getValue();
            LocalDateTime starts = startDateTime();
            LocalDateTime ends = endDateTime();
            Contacts contact = addAppointmentContact.getValue();
            Customers customerID = addAppointmentCustID.getValue();
            Users userID = addAppointmentUserID.getValue();

            DAOAppointments.addAppointment(title, description, location, type, starts, ends, customerID.getCustomerID(), userID.getUserID(), contact.getContactName());
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
            stage.setTitle("Appointments");
            stage.setScene(new Scene(scene));
            stage.show();
            }
    }

    /**
     * Method initializes and populates combo boxes.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateTypeCB();
            populateTimeCB();
            populateContactCB();
            populateCustomerIDCB();
            populateUserIDCB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
