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
        DBConnection.closeConnection();
    }
}
