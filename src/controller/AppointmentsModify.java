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

public class AppointmentsModify implements Initializable {

    Stage stage;
    Parent scene;
    public static Appointments selectedAppointments;
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

    // Populates Contact combo box *************************************************************************************
    private void populateContactCB() throws SQLException {
//        ObservableList<String> contactList = FXCollections.observableArrayList();
//        try {
//            ObservableList<Contacts> contacts = DAOContacts.getAllContacts();
//            for (Contacts c : contacts) {
//                contactList.add(c.getContactName());
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        modAppointmentContact.setItems(contactList);
        modAppointmentContact.setItems(DAOContacts.getAllContacts());
    }

    // Populates Type combo box ****************************************************************************************
    private void populateTypeCB() {
        ObservableList<String> typeList = FXCollections.observableArrayList();
        typeList.addAll("Planning Session", "De-Briefing", "Informal", "Phone Call", "Priority", "Brainstorm");
        modAppointmentType.setItems(typeList);
    }

    // Method combines selected date and start time ********************************************************************
    public LocalDateTime startDayTime() {
        LocalTime startTime = modAppointmentStart.getSelectionModel().getSelectedItem();
        LocalDate appointmentDay = modAppointmentDate.getValue();
        return LocalDateTime.of(appointmentDay, startTime);
    }

    // Method combines selected date and end time **********************************************************************
    public LocalDateTime endDayTime() {
        LocalTime endTime = modAppointmentEnd.getSelectionModel().getSelectedItem();
        LocalDate appointmentDay = modAppointmentDate.getValue();
        return LocalDateTime.of(appointmentDay, endTime);
    }

    // Method creates business hours ***********************************************************************************
    public ObservableList<LocalTime> hoursOfBusiness() {
        ObservableList<LocalTime> hoursList = FXCollections.observableArrayList();
        LocalTime openHoursEST = LocalTime.of(8, 0);
        LocalTime closeHoursEST = LocalTime.of(22, 0);
        ZoneId ESTTime = ZoneId.of("America/New_York");
        ZoneId localDefaultTime = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime openTimes = ZonedDateTime.of(LocalDate.now(), openHoursEST, ESTTime);
        ZonedDateTime closedTimes = ZonedDateTime.of(LocalDate.now(), closeHoursEST, ESTTime);
        ZonedDateTime openBusinessHours = openTimes.withZoneSameInstant(localDefaultTime);
        ZonedDateTime closedBusinessHours = closedTimes.withZoneSameInstant(localDefaultTime);
        ZonedDateTime tms = openBusinessHours.minusMinutes(30);
        while (tms.isBefore(closedBusinessHours)) {
            tms = tms.plusMinutes(30);
            hoursList.add(LocalTime.from(tms));
            if ((tms.equals(closedBusinessHours) || tms.isAfter(closedBusinessHours))) {
                break;
            }
        }
        return hoursList;
    }

    // Populates Start and End Time combo boxes in 30 minute increments ************************************************
    private void populateTimeCB() {
        modAppointmentStart.setItems(hoursOfBusiness());
        modAppointmentEnd.setItems(hoursOfBusiness());
    }

    // Populates Customer ID combo box *********************************************************************************
    private void populateCustomerIDCB() throws SQLException {
        modAppointmentCustID.setItems(DAOCustomers.getAllCustomers());
    }

    // Populates User ID combo box *************************************************************************************
    private void populateUserIDCB() throws SQLException {
//        ObservableList<Integer> usersIDList = FXCollections.observableArrayList();
//        ObservableList<Users>userList = DAOUSERS.getAllUsers();
//        for (Users u : userList) {
//            usersIDList.add(u.getUserID());
//        }
//        modAppointmentUserID.setItems(usersIDList);
        modAppointmentUserID.setItems(DAOUsers.getAllUsers());
    }

    // Accepts selected customer from Customers screen *****************************************************************
    public static void modSelectedAppointment2(Appointments appointment) {
        selectedAppointments = appointment;
    }

    // Populates table with selected appointment ***********************************************************************
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

    // Goes back to Appointments screen ********************************************************************************
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

    // Save changes to selected appointment *****************************************************************************************
    @FXML void onSaveButton(ActionEvent event) {
        String title = modAppointmentTitle.getText();
        String description = modAppointmentDescription.getText();
        String location = modAppointmentLocation.getText();
        String type = modAppointmentType.getValue();
        String appointments = modAppointmentID.getText();
        LocalDateTime starts = startDayTime();
        LocalDateTime ends = endDayTime();
        Contacts contact = modAppointmentContact.getValue();
        Customers customerID = modAppointmentCustID.getValue();
        Users userID = modAppointmentUserID.getValue();

        try {
            DAOAppointments.updateAppointment(title, description, location, type, starts, ends, customerID.getCustomerID(), userID.getUserID(), contact.getContactName(), appointments);
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
            stage.setTitle("Appointments");
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override //********************************************************************************************************
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
