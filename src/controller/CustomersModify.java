package controller;
import database.DAOCountries;
import database.DAOCustomers;
import database.DAODivisions;
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
import model.Countries;
import model.Customers;
import model.Divisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Modify Customers controller class.
 */
public class CustomersModify implements Initializable {
    Stage stage;
    Parent scene;

    public static Customers selectedCustomer;
    @FXML private TextField modCustomerID;
    @FXML private TextField modName;
    @FXML private TextField modAddress;
    @FXML private TextField modPhone;
    @FXML private TextField modPostalCode;
    @FXML private ComboBox<String> modCountryCB;
    @FXML private ComboBox<String> modDivisionCB;

    /**
     * Method populates country combo box
     */
    private void populateCountryComboBox(){
        ObservableList<String> countryList = FXCollections.observableArrayList();
        try {
            ObservableList<Countries> countries = DAOCountries.getAllCountries();
            for (Countries c : countries) {
                countryList.add(c.getCountryName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        modCountryCB.setItems(countryList);
    }

    /**
     * Method populates divisions combo box.
     */
    private void populateDivisionComboBox(){
        ObservableList <String> divisionList = FXCollections.observableArrayList();
        try {
            ObservableList<Divisions> divisions = DAODivisions.getAllDivisions();
            for (Divisions d: divisions) {
                divisionList.add(d.getDivision());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        modDivisionCB.setItems(divisionList);
    }

    /**
     * Method selects divisions based on country selected.
     * @param event
     */
    @FXML private void sortDivisions (ActionEvent event) {
        ObservableList<String> divisionsList = FXCollections.observableArrayList();
        try {
            ObservableList<Divisions> divisions = DAODivisions.getDivisionByCountry(modCountryCB.getSelectionModel().getSelectedItem());
            for (Divisions d : divisions) {
                divisionsList.add(d.getDivision());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        modDivisionCB.setItems(divisionsList);
    }

    /**
     * Method receives selected customer from Customers screen.
     * @param customer
     */
    public static void modCustomer (Customers customer) {
        selectedCustomer = customer;
    }

    /**
     * Method sends user back ti Customers screen.
     * @param event
     * @throws IOException
     */
    @FXML void onCancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel the update, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
            stage.setTitle("Customers");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Method saves changes to selected customer.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML void onSaveButton(ActionEvent event) throws SQLException, IOException {
        int customerID = Integer.parseInt(modCustomerID.getText());
        String customerName = modName.getText();
        String customerAddress = modAddress.getText();
        String customerPostalCode = modPostalCode.getText();
        String customerPhone = modPhone.getText();
        String divisions = modDivisionCB.getValue();

        if (modAddress.getText().isEmpty() || modName.getText().isEmpty() || modPhone.getText().isEmpty() || modPostalCode.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please make sure no fields are blank.");
            alert.showAndWait();
        } else {
            try{
                boolean customerCreated = DAOCustomers.updateCustomer(customerName, customerAddress, customerPostalCode, customerPhone, divisions, customerID);
                if (customerCreated) {
                    stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
                    stage.setTitle("Customers");
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method initializes and populates combo boxes and text fields.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateCountryComboBox();
        populateDivisionComboBox();

        // Populate fields with customer data
        modCustomerID.setText(Integer.toString(selectedCustomer.getCustomerID()));
        modName.setText(selectedCustomer.getCustomerName());
        modAddress.setText(selectedCustomer.getAddress());
        modPhone.setText(selectedCustomer.getPhoneNumber());
        modPostalCode.setText(selectedCustomer.getPostalCode());
        modCountryCB.getSelectionModel().select(selectedCustomer.getCountry());
        modDivisionCB.getSelectionModel().select(selectedCustomer.getDivision());
    }

}
