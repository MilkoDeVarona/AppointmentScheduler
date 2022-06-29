package controller;

import database.DAOCustomers;
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

public class Customers implements Initializable {
    Stage stage;
    Parent scene;

    static ObservableList <model.Customers> customerInfo;
    @FXML private TableView<model.Customers> customersTable;
    @FXML private TableColumn<?, ?> customersColumnAddress;
    @FXML private TableColumn<?, ?> customersColumnCountry;
    @FXML private TableColumn<?, ?> customersColumnDivision;
    @FXML private TableColumn<?, ?> customersColumnID;
    @FXML private TableColumn<?, ?> customersColumnName;
    @FXML private TableColumn<?, ?> customersColumnPhone;
    @FXML private TableColumn<?, ?> customersColumnPostalCode;

    // Back to home button
    @FXML
    void onBackButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
        stage.setTitle("Home");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    // Add a new customer button
    @FXML
    void onAddCustomerButton(ActionEvent event) throws IOException{
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomersAdd.fxml"));
        stage.setTitle("Add Customers");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    // Modify an existing customer button
    @FXML
    void onModifyCustomerButton(ActionEvent event) throws IOException {
        model.Customers toModify = customersTable.getSelectionModel().getSelectedItem();
        if (toModify == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select a customer to modify");
            alert.showAndWait();
        } else {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/CustomersModify.fxml"));
            stage.setTitle("Modify Customers");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    // Delete the selected customer button
    @FXML
    void onDeleteCustomerButton(ActionEvent event) throws SQLException {
        model.Customers selected = customersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select a customer to delete");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected customer, do you want to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                model.Customers c = customersTable.getSelectionModel().getSelectedItem();
                DAOCustomers.deleteCustomer(c.getCustomerID());
                customerInfo = DAOCustomers.getAllCustomers();
                customersTable.setItems(customerInfo);
                customersTable.refresh();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerInfo = DAOCustomers.getAllCustomers();
            customersTable.setItems(customerInfo);
            customersColumnID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customersColumnName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customersColumnPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            customersColumnCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
            customersColumnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            customersColumnDivision.setCellValueFactory(new PropertyValueFactory<>("division"));
            customersColumnPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
