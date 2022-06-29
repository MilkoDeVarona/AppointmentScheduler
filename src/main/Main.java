package main;

import database.DAOCountries;
import database.DAODivisions;
import database.DBConnection;
import database.DAOCustomers;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root  = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setTitle("Log In");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main (String[] args) throws SQLException {
        DBConnection.startConnection();
        launch(args);

        //DAODivisions.getDivisionByName("Idaho");
        //DAOCountries.getCountryByName("U.S");
        //DAOCountries.getCountryByName("Canada");
        //DAODivisions.getDivisionByCountryID(2);
        //DAOCustomers.addCustomer("Milko", "Some St 123", "145263", "5558989", 3);
        //DAOCustomers.updateCustomer("Milko", "Same st 12", "6689", "", 15, 8);
        //DAOCustomers.addCustomer("John Wick", "Danger St 69", "4587", "963369963", 36);
        //DAOCustomers.updateCustomer("Milko", "Some St. 123", "6969", "3057896969", 9, 8);
        //DAOCustomers.deleteCustomer(10);
        //DAOCountries.getAllCountries();
        //DAODivisions.getDivisionByCountry("Canada");

        DBConnection.closeConnection();
    }

}
