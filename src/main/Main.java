package main;

import database.FruitsQuery;
import database.JDBC;
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
        stage.setTitle("Scheduling App");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main (String[] args) throws SQLException {
        JDBC.openConnection();

        launch(args);

        /*int rowsAffected = FruitsQuery.delete(7);
        if (rowsAffected > 0) {
            System.out.println("Delete successful!");
        } else {
            System.out.println("Delete failed!");
        }*/

        // FruitsQuery.select(3);

        JDBC.closeConnection();
    }

}
