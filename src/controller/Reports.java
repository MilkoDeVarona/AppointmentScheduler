package controller;
import database.DAOAppointments;
import database.DAOContacts;
import database.DAOCountries;
import database.DAOCustomers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Countries;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Reports controller class.
 */
public class Reports implements Initializable {
    Stage stage;
    Parent scene;
    @FXML private ComboBox<String> reportsCBAppointments;
    @FXML private ComboBox<String> reportsCBMonth;
    @FXML private ComboBox<Contacts> reportsCBContact;
    @FXML private ComboBox<Countries> reportsCBCountry;
    @FXML private TableView<Appointments> reportsCtcTable;
    @FXML private TableColumn<?, ?> reportsClmApptID;
    @FXML private TableColumn<?, ?> reportsClmCustID;
    @FXML private TableColumn<?, ?> reportsClmDescription;
    @FXML private TableColumn<?, ?> reportsClmEnd;
    @FXML private TableColumn<?, ?> reportsClmStart;
    @FXML private TableColumn<?, ?> reportsClmTitle;
    @FXML private TableColumn<?, ?> reportsClmType;
    @FXML private Label reportsTotalByTypeMonth;
    @FXML private Label reportsTotalByCountry;

    // Report 1 ********************************************************************************************************

    /**
     * Method populates Type combo box.
     */
    private void populateTypeCB() {
        ObservableList<String> typeList = FXCollections.observableArrayList("Planning Session", "De-Briefing", "Informal", "Phone Call", "Priority", "Brainstorm");
        reportsCBAppointments.setItems(typeList);
    }

    /**
     * Method populates Month combo box.
     */
    private void populateMonthCB() {
        ObservableList<String> monthList = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        reportsCBMonth.setItems(monthList);
    }

    /**
     * Method generates total number of appointments by month and type.
     * @param event
     */
    @FXML void onTotalApptmsGenerate(ActionEvent event) {
        String type = reportsCBAppointments.getValue();
        String month = reportsCBMonth.getValue();
        if (type == null && month == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select a month and a type");
            alert.showAndWait();
        } else if (type == null && month != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select a type");
            alert.showAndWait();
        } else if (month == null && type != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select a month");
            alert.showAndWait();
        } else {
            int total = DAOAppointments.getTotalAppointmentsByTypeAndMonth(type, month);
            reportsTotalByTypeMonth.setText("The total number of " + "'" + type + "'" + " appointments for the month of " + month + " is " + total);
        }
    }


    // Report 2 ********************************************************************************************************

    /**
     * Method populates Contact combo box.
     * @throws SQLException
     */
    private void populateContactCB() throws SQLException {
        reportsCBContact.setItems(DAOContacts.getAllContacts());
    }

    /**
     * Method filters appointments by contact.
     * <p>Lambda expression returns a contact to the filtered list if its ID matches the ID of the contact selected in the combo box.</p>
     * @return
     * @throws SQLException
     */
    public ObservableList<Appointments> updateTable () throws SQLException {
        ObservableList<Appointments> getAllAppointments = DAOAppointments.viewAllAppointments();
        FilteredList<Appointments> appointmentsByContact = new FilteredList<>(getAllAppointments, fl -> fl.getContactID() == reportsCBContact.getSelectionModel().getSelectedItem().getContactID());
        return appointmentsByContact;
    }

    /**
     * Method shows all appointments for the selected contact.
     * @param event
     * @throws SQLException
     */
    @FXML void onAppointmentsByContact(ActionEvent event) throws SQLException {
        reportsCtcTable.setItems(updateTable());
    }


    // Report 3 ********************************************************************************************************

    /**
     * Method populates Country combo box.
     * @throws SQLException
     */
    private void populateCountryCB () throws SQLException {
        reportsCBCountry.setItems(DAOCountries.getAllCountries());
    }

    /**
     * Method dhows total number of customers per country.
     * @param event
     */
    @FXML void onTotalCustomersByCountry(ActionEvent event) {
        String country = reportsCBCountry.getSelectionModel().getSelectedItem().getCountryName();
        int total = DAOCustomers.getTotalCustomersByCountry(country);
        reportsTotalByCountry.setText(country + " has a total of " + String.valueOf(total) + " customers.");
    }

    // *****************************************************************************************************************

    /**
     * Method sends user back to Home screen.
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
     * Method initializes reports screens and populates fields.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Report 1
            populateTypeCB();
            populateMonthCB();

            // Report 2
            populateContactCB();
            reportsCtcTable.setItems(DAOAppointments.viewAllAppointments());
            reportsClmApptID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            reportsClmTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            reportsClmType.setCellValueFactory(new PropertyValueFactory<>("type"));
            reportsClmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            reportsClmStart.setCellValueFactory(new PropertyValueFactory<>("start"));
            reportsClmEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
            reportsClmCustID.setCellValueFactory(new PropertyValueFactory<>("customerID"));

            // Report 3
            populateCountryCB();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
