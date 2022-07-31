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
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.TimeZone;

/**
 * The main class. Launches the application.
 */
public class Main extends Application {

    /**
     * Start method. Creates the stage and loads the first scene.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root  = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setTitle("Log In");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Main method. The first method to be called when the application is run.
     * @param args
     * @throws SQLException
     */
    public static void main (String[] args) throws SQLException {
        //Locale.setDefault(new Locale("fr"));
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }

}
