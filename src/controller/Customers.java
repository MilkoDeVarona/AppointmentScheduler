package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class Customers {

    Stage stage;
    Parent scene;

    @FXML private TableView<?> customersTable;
    @FXML private TableColumn<?, ?> customersColumnAddress;
    @FXML private TableColumn<?, ?> customersColumnCountry;
    @FXML private TableColumn<?, ?> customersColumnDivision;
    @FXML private TableColumn<?, ?> customersColumnID;
    @FXML private TableColumn<?, ?> customersColumnName;
    @FXML private TableColumn<?, ?> customersColumnPhone;
    @FXML private TableColumn<?, ?> customersColumnPostalCode;

    @FXML
    void onBackButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
        stage.setTitle("Home");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onAddCustomerButton(ActionEvent event) throws IOException{
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomersAdd.fxml"));
        stage.setTitle("Add Customers");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onModifyCustomerButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomersModify.fxml"));
        stage.setTitle("Modify Customers");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onDeleteCustomerButton(ActionEvent event) {

    }

}
