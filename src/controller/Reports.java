package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Reports {

    Stage stage;
    Parent scene;

    @FXML private ComboBox<?> reportsCBAppointments;
    @FXML private ComboBox<?> reportsCBContact;
    @FXML private ComboBox<?> reportsCBCountry;
    @FXML private ComboBox<?> reportsCBMonth;

    @FXML private TableView<?> reportsCtcTable;
    @FXML private TableColumn<?, ?> reportsClmApptID;
    @FXML private TableColumn<?, ?> reportsClmCustID;
    @FXML private TableColumn<?, ?> reportsClmDescription;
    @FXML private TableColumn<?, ?> reportsClmEnd;
    @FXML private TableColumn<?, ?> reportsClmStart;
    @FXML private TableColumn<?, ?> reportsClmTitle;
    @FXML private TableColumn<?, ?> reportsClmType;

    @FXML private Label reportsTotalByCountry;
    @FXML private Label reportsTotalByTypeMonth;

    @FXML void onBackButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
        stage.setTitle("Home");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML void onTotalApptmsGenerate(ActionEvent event) {

    }

}
