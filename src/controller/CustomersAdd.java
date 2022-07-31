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
import model.Divisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Add Customers controller class.
 */
public class CustomersAdd implements Initializable {
    Stage stage;
    Parent scene;

    @FXML private TextField addCustomerID;
    @FXML private TextField addAddress;
    @FXML private TextField addName;
    @FXML private TextField addPhone;
    @FXML private TextField addPostalCode;
    @FXML private ComboBox<String> addCountryCB;
    @FXML private ComboBox<String> addDivisionCB;

    /**
     * Method populates country combo box
     */
    private void populateCountryCB(){
        ObservableList<String> countryList = FXCollections.observableArrayList();
        try {
            ObservableList<Countries> countries = DAOCountries.getAllCountries();
            for (Countries c : countries) {
                countryList.add(c.getCountryName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        addCountryCB.setItems(countryList);
    }

    /**
     * Method populates divisions combo box.
     * <p>Lambda expression waits for country combo box to be selected to populate divisions combo box.</p>
     */
    private void populateDivisionCB(){
        addCountryCB.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                addDivisionCB.getItems().clear();
                addDivisionCB.setDisable(true);
            } else {
                try {
                    addDivisionCB.setDisable(false);
                    addDivisionCB.setItems(DAOCustomers.getDivisionsByCountry(addCountryCB.getValue()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Method selects divisions based on country selected.
     * @param event
     */
    @FXML private void sortDivisions (ActionEvent event) {
        ObservableList<String> divisionsList = FXCollections.observableArrayList();
        try {
            ObservableList<Divisions> divisions = DAODivisions.getDivisionByCountry(addCountryCB.getSelectionModel().getSelectedItem());
            for (Divisions d : divisions) {
                divisionsList.add(d.getDivision());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        addDivisionCB.setItems(divisionsList);
    }

    /**
     * Method sends user back to Customers screen.
     * @param event
     * @throws IOException
     */
    @FXML void onCancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");
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
     * Method saves a new customer.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML void onSaveButton(ActionEvent event) throws SQLException, IOException {
        String customerName = addName.getText();
        String customerAddress = addAddress.getText();
        String customerPostalCode = addPostalCode.getText();
        String customerPhone = addPhone.getText();
        String divisions = addDivisionCB.getValue();

        if (addAddress.getText().isEmpty() || addName.getText().isEmpty() || addPhone.getText().isEmpty() || addPostalCode.getText().isEmpty() || addDivisionCB.getSelectionModel().isEmpty() || addCountryCB.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Please make sure no fields are blank.");
            alert.showAndWait();
        } else {
            try{
                boolean customerCreated = DAOCustomers.addCustomer(customerName, customerAddress, customerPostalCode, customerPhone, divisions);
                if (customerCreated) {
                    stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
                    stage.setTitle("Customers");
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method initializes and populates combo boxes.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateCountryCB();
        populateDivisionCB();
    }

}
