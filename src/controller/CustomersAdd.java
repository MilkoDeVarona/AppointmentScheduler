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

public class CustomersAdd implements Initializable {
    Stage stage;
    Parent scene;

    //@FXML private TextField addCustomerID;
    @FXML private TextField addAddress;
    @FXML private TextField addName;
    @FXML private TextField addPhone;
    @FXML private TextField addPostalCode;
    @FXML private ComboBox<String> addCountryCB;
    @FXML private ComboBox<String> addDivisionCB;

    // Populates country combo box *************************************************************************************
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

    // Populates divisions combo box ***********************************************************************************
    private void populateDivisionCB(){
        ObservableList <String> divisionList = FXCollections.observableArrayList();
        try {
            ObservableList<Divisions> divisions = DAODivisions.getAllDivisions();
            for (Divisions d: divisions) {
                divisionList.add(d.getDivision());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        addDivisionCB.setItems(divisionList);
    }

    // Selects divisions based on country selected *********************************************************************
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

        addDivisionCB.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty) ;
                if (empty || item == null) {
                    setText("Select Subject");
                } else {
                    setText(item);
                }
            }
        });
    }

    // Goes back to Customers screen ***********************************************************************************
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

    // Saves a new customer ********************************************************************************************
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

    @Override /********************************************************************************************************/
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateCountryCB();
        populateDivisionCB();
    }

}
