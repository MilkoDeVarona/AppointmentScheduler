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
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Customers controller class.
 */
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

    /**
     * Method sends user back to Home screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void onBackButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
        stage.setTitle("Home");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method send user to Add Customer screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void onAddCustomerButton(ActionEvent event) throws IOException{
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomersAdd.fxml"));
        stage.setTitle("Add Customers");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method sends user to Modify Customer screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void onModifyCustomerButton(ActionEvent event) throws IOException {
        CustomersModify.modCustomer(customersTable.getSelectionModel().getSelectedItem());
        if (customersTable.getSelectionModel().getSelectedItem() == null) {
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

    /**
     * Method deletes selected customer.
     * @param event
     * @throws SQLException
     */
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

    /**
     * Method initializes customer table.
     * @param url
     * @param resourceBundle
     */
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
