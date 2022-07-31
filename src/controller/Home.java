package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Home controller class.
 */
public class Home {
    Stage stage;
    Parent scene;

    /**
     * Method sends user to Customers screen.
     * @param event
     * @throws IOException
     */
    @FXML public void onCustomersButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        stage.setTitle("Customers");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method sends user to Appointments screen.
     * @param event
     * @throws IOException
     */
    @FXML public void onAppointmentsButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setTitle("Appointments");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method sends user to Reports screen.
     * @param event
     * @throws IOException
     */
    @FXML public void onReportsButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        stage.setTitle("Reports");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method send user to login screen.
     * @param event
     * @throws IOException
     */
    @FXML public void onLogOutButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setTitle("Log In");
        stage.setScene(new Scene(scene));
        stage.show();
    }

}
