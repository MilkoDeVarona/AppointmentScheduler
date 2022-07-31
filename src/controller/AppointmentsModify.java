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
 * Modify appointments controller class.
 */
public class AppointmentsModify implements Initializable {
    Stage stage;
    Parent scene;
    @FXML private DatePicker modAppointmentDate;
    @FXML private TextField modAppointmentDescription;
    @FXML private TextField modAppointmentID;
    @FXML private TextField modAppointmentTitle;
    @FXML private TextField modAppointmentLocation;
    @FXML private ComboBox<LocalTime> modAppointmentEnd;
    @FXML private ComboBox<LocalTime> modAppointmentStart;
    @FXML private ComboBox<Contacts> modAppointmentContact;
    @FXML private ComboBox<String> modAppointmentType;
    @FXML private ComboBox<Users> modAppointmentUserID;
    @FXML private ComboBox<Customers> modAppointmentCustID;

    /**
     * Method populates Contact combo box.
     * @throws SQLException
     */
    private void populateContactCB() throws SQLException {
        modAppointmentContact.setItems(DAOContacts.getAllContacts());
    }

    /**
     * Method creates list of appointment types to populate Type combo box.
     */
    private void populateTypeCB() {
        ObservableList<String> typeList = FXCollections.observableArrayList();
        typeList.addAll("Planning Session", "De-Briefing", "Informal", "Phone Call", "Priority", "Brainstorm");
        modAppointmentType.setItems(typeList);
    }

    /**
     * Method combines selected date and start time into a start LocalDateTime object.
     * @return
     */
    public LocalDateTime startDateTime() {
        LocalTime startTime = modAppointmentStart.getSelectionModel().getSelectedItem();
        LocalDate appointmentDateTime = modAppointmentDate.getValue();
        return LocalDateTime.of(appointmentDateTime, startTime);
    }

    /**
     * Method combines selected date and end time into an end LocalDateTime object.
     * @return
     */
    public LocalDateTime endDateTime() {
        LocalTime endTime = modAppointmentEnd.getSelectionModel().getSelectedItem();
        LocalDate appointmentDateTime = modAppointmentDate.getValue();
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
        modAppointmentStart.setItems(hoursOfBusiness());
        modAppointmentEnd.setItems(hoursOfBusiness());
    }

    /**
     * Populates Customer ID combo box.
     * @throws SQLException
     */
    private void populateCustomerIDCB() throws SQLException {
        modAppointmentCustID.setItems(DAOCustomers.getAllCustomers());
    }

    /**
     * Populates User ID combo box.
     * @throws SQLException
     */
    private void populateUserIDCB() throws SQLException {
        modAppointmentUserID.setItems(DAOUsers.getAllUsers());
    }

    /**
     * Method checks for empty fields and overlapping appointments when creating an appointment.
     * @param appts
     * @return
     * @throws SQLException
     */
    public boolean alerts (String appts) throws SQLException {
        if (modAppointmentTitle.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Title field can not be empty");
            alert.showAndWait();
            return false;
        }

        if (modAppointmentDescription.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Description field can not be empty");
            alert.showAndWait();
            return false;
        }

        if (modAppointmentLocation.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Location field can not be empty");
            alert.showAndWait();
            return false;
        }

        if (modAppointmentContact.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please select a contact");
            alert.showAndWait();
            return false;
        }

        if (modAppointmentType.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please select an appointment type");
            alert.showAndWait();
            return false;
        }

        if (modAppointmentDate.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please select an appointment date");
            alert.showAndWait();
            return false;
        }

        if (modAppointmentStart.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please select an appointment start time");
            alert.showAndWait();
            return false;
        }

        if (modAppointmentEnd.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please select an appointment end time");
            alert.showAndWait();
            return false;
        }

        if (modAppointmentCustID.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please select a customer");
            alert.showAndWait();
            return false;
        }

        if (modAppointmentUserID.getSelectionModel().isEmpty()) {
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
        return true;
    }

    /**
     * Method receives the selected appointment from the Appointments screen and sets field values with the corresponding data.
     * @param apptm
     */
    public void modSelectedAppointment (Appointments apptm) {
        modAppointmentID.setText(String.valueOf(apptm.getAppointmentID()));
        modAppointmentTitle.setText(apptm.getTitle());
        modAppointmentDescription.setText(apptm.getDescription());
        modAppointmentLocation.setText(apptm.getLocation());
        for (Contacts c : modAppointmentContact.getItems()) {
            if (apptm.getContactID() == c.getContactID()) {
                modAppointmentContact.setValue(c);
                break;
            }
        }
        modAppointmentType.setValue(apptm.getType());
        modAppointmentDate.setValue(apptm.getStart().toLocalDate());
        modAppointmentStart.setValue(apptm.getStart().toLocalTime());
        modAppointmentEnd.setValue(apptm.getEnd().toLocalTime());
        for (Customers c : modAppointmentCustID.getItems()) {
            if (apptm.getCustomerID() == c.getCustomerID()) {
                modAppointmentCustID.setValue(c);
                break;
            }
        }
        for (Users u : modAppointmentUserID.getItems()) {
            if (apptm.getUserID() == u.getUserID()) {
                modAppointmentUserID.setValue(u);
                break;
            }
        }
    }

    /**
     * Method cancels modify appointment action and sends user back to Appointments screen.
     * @param event
     * @throws IOException
     */
    @FXML void onCancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel the update, do you want to continue?");
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
     * Method saves changes to selected appointment.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML void onSaveButton(ActionEvent event) throws SQLException, IOException {
        boolean valid = alerts(modAppointmentID.getText());
        if (valid) {
            String title = modAppointmentTitle.getText();
            String description = modAppointmentDescription.getText();
            String location = modAppointmentLocation.getText();
            String type = modAppointmentType.getValue();
            String appointments = modAppointmentID.getText();
            LocalDateTime starts = startDateTime();
            LocalDateTime ends = endDateTime();
            Contacts contact = modAppointmentContact.getValue();
            Customers customerID = modAppointmentCustID.getValue();
            Users userID = modAppointmentUserID.getValue();

            DAOAppointments.updateAppointment(title, description, location, type, starts, ends, customerID.getCustomerID(), userID.getUserID(), contact.getContactName(), appointments);
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
