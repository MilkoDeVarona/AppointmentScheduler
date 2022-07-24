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
        addAppointmentContact.setItems(DAOContacts.getAllContacts());
    }

    // Populates Type combo box ****************************************************************************************
    private void populateTypeCB() {
        ObservableList<String> typeList = FXCollections.observableArrayList();
        typeList.addAll("Planning Session", "De-Briefing", "Informal", "Phone Call", "Priority", "Brainstorm");
        addAppointmentType.setItems(typeList);
    }

    // Method combines selected date and start time ********************************************************************
    public LocalDateTime startDayTime() {
        LocalTime startTime = addAppointmentStart.getSelectionModel().getSelectedItem();
        LocalDate appointmentDay = addAppointmentDate.getValue();
        return LocalDateTime.of(appointmentDay, startTime);
    }

    // Method combines selected date and end time **********************************************************************
    public LocalDateTime endDayTime() {
        LocalTime endTime = addAppointmentEnd.getSelectionModel().getSelectedItem();
        LocalDate appointmentDay = addAppointmentDate.getValue();
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
        addAppointmentStart.setItems(hoursOfBusiness());
        addAppointmentEnd.setItems(hoursOfBusiness());

//        ObservableList<String> timeList = FXCollections.observableArrayList();
//        LocalTime startTime = LocalTime.of(7, 0);
//        LocalTime endTime = LocalTime.of(23, 0);
//        timeList.add(startTime.toString());
//        while (startTime.isBefore(endTime)) {
//            startTime = startTime.plusMinutes(15);
//            timeList.add(startTime.toString());
//        }
//        addAppointmentStart.setItems(timeList);
//        addAppointmentEnd.setItems(timeList);


//        addAppointmentStart.getItems().addAll("08:00", "08:15", "08:30", "08:45",
//                "09:00", "09:15", "09:30", "09:45", "10:00", "10:15", "10:30", "10:45",
//                "11:00", "11:15", "11:30", "11:45", "12:00", "12:15", "12:30", "12:45",
//                "13:00", "13:15", "13:30", "13:45", "14:00", "14:15", "14:30", "14:45",
//                "15:00", "15:15", "15:30", "15:45", "16:00", "16:15", "16:30", "16:45");
//        addAppointmentEnd.getItems().addAll("08:15", "08:30", "08:45", "09:00",
//                "09:15", "09:30", "09:45", "10:00", "10:15", "10:30", "10:45", "11:00",
//                "11:15", "11:30", "11:45", "12:00", "12:15", "12:30", "12:45", "13:00",
//                "13:15", "13:30", "13:45", "14:00", "14:15", "14:30", "14:45", "15:00",
//                "15:15", "15:30", "15:45", "16:00", "16:15", "16:30", "16:45", "17:00");
    }

    // Populates Customer ID combo box *********************************************************************************
    private void populateCustomerIDCB() throws SQLException {
//        ObservableList<Integer> customerIDList = FXCollections.observableArrayList();
//        try {
//            ObservableList<Customers> customerslist = DAOCustomers.getAllCustomers();
//            for (Customers c : customerslist) {
//                customerIDList.add(c.getCustomerID());
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        addAppointmentCustID.setItems(DAOCustomers.getAllCustomers());
    }

    // Populates User ID combo box *************************************************************************************
    private void populateUserIDCB() throws SQLException {
//        ObservableList<Integer> userIDList = FXCollections.observableArrayList();
//        ObservableList<Users> usersList = DAOUSERS.getAllUsers();
//        for (Users u : usersList) {
//            userIDList.add(u.getUserID());
//        }
//        addAppointmentUserID.setItems(userIDList);
        addAppointmentUserID.setItems(DAOUsers.getAllUsers());
    }

    // Goes back to Appointments screen ********************************************************************************
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

    // Saves a new appointment *****************************************************************************************
    @FXML void onSaveButton(ActionEvent event) throws SQLException, IOException{
        String title = addAppointmentTitle.getText();
        String description = addAppointmentDescription.getText();
        String location = addAppointmentLocation.getText();
        String type = addAppointmentType.getValue();
        LocalDateTime starts = startDayTime();
        LocalDateTime ends = endDayTime();
        Contacts contact = addAppointmentContact.getValue();
        Customers customerID = addAppointmentCustID.getValue();
        Users userID = addAppointmentUserID.getValue();

        if (addAppointmentTitle.getText().isEmpty() || addAppointmentDescription.getText().isEmpty() || addAppointmentLocation.getText().isEmpty() ||
                addAppointmentContact.getSelectionModel().isEmpty() || addAppointmentContact.getSelectionModel().isEmpty() || addAppointmentType.getSelectionModel().isEmpty() ||
                addAppointmentDate.getChronology().equals(null) || addAppointmentStart.getSelectionModel().isEmpty() || addAppointmentEnd.getSelectionModel().isEmpty() ||
                addAppointmentCustID.getSelectionModel().isEmpty() || addAppointmentUserID.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please make sure no fields are blank.");
            alert.showAndWait();
        } else if (startDayTime().isAfter(ends) || startDayTime().isEqual(endDayTime())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Start time can not be equal or after end time.");
            alert.showAndWait();
        } else {
            try {
                DAOAppointments.addAppointment(title, description, location, type, starts, ends, customerID.getCustomerID(), userID.getUserID(), contact.getContactName());
                stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
                stage.setTitle("Appointments");
                stage.setScene(new Scene(scene));
                stage.show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
