package controller;
import database.DAOAppointments;
import database.DAOUsers;
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
import model.Appointments;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Login controller class.
 */
public class Login implements Initializable {
    Stage stage;
    Parent scene;
    ResourceBundle rb;
    @FXML private Label loginLabel;
    @FXML private Label zoneLabel;
    @FXML private Label loginErrorLabel;
    @FXML private PasswordField passwordTextField;
    @FXML private TextField userNameTextField;
    @FXML private Button loginButton;
    @FXML private Label locationLabel;

    /**
     * Method creates a file log of successful log ins.
     * @throws IOException
     */
    public void loginActivitySuccessful () throws IOException {
        PrintWriter printwriter = new PrintWriter(new FileOutputStream(new File("login_activity.txt"),true));
        printwriter.append("User " +"'"+ userNameTextField.getText() + "' successfully logged in at " + LocalDate.now() + " " + LocalTime.now().truncatedTo(ChronoUnit.MINUTES) + "\n");
        printwriter.close();
    }

    /**
     * Method creates a file log of unsuccessful log ins.
     * @throws IOException
     */
    public void loginActivityNotSuccessful () throws IOException {
        PrintWriter printwriter = new PrintWriter(new FileOutputStream(new File("login_activity.txt"),true));
        printwriter.append("User " +"'"+ userNameTextField.getText() + "' did not successfully log in at " + LocalDate.now() + " " + LocalTime.now().truncatedTo(ChronoUnit.MINUTES) + "\n");
        printwriter.close();
    }

    /**
     * Method alerts for existing appointments within 15 minutes.
     * @throws SQLException
     */
    private void existingUpcomingAppointment() throws SQLException {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime currentTime15 = LocalDateTime.now().plusMinutes(15);
        ObservableList<Appointments> upcomingAppointmentsList = FXCollections.observableArrayList();
        boolean hasUpcomingApp = false;
        int upcomingID = 0;
        LocalDateTime upcomingTime = LocalDateTime.now();

        try {
            ObservableList<Appointments> allAppointmentsList = DAOAppointments.viewAllAppointments();
            if (upcomingAppointmentsList != null) {
                for (Appointments a : allAppointmentsList) {
                    if (a.getStart().isAfter(currentTime) && a.getStart().isBefore(currentTime15)) {
                        upcomingAppointmentsList.add(a);
                        hasUpcomingApp = true;
                        upcomingID = a.getAppointmentID();
                        upcomingTime = a.getStart();
                    }
                }

                if (hasUpcomingApp) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Alert");
                    alert.setHeaderText("Appointment alert");
                    alert.setContentText(
                            "You have an upcoming appointment"  + "\n" +
                                    "Appointment #: " + upcomingID +  "\n" +
                                    "Appointment time: " + upcomingTime + "\n"
                    );
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Alert");
                    alert.setHeaderText("Appointment alert");
                    alert.setContentText("You have no appointments scheduled for the next 15 minutes.");
                    alert.showAndWait();

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method logs into application.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void onSubmitButtonAction(ActionEvent actionEvent) throws IOException {
        if (userNameTextField.getText().isBlank() && passwordTextField.getText().isBlank()) {
            loginErrorLabel.setText(rb.getString("1"));
        } else if (userNameTextField.getText().isBlank()) {
            loginErrorLabel.setText(rb.getString("2"));
        } else if (passwordTextField.getText().isBlank()) {
            loginErrorLabel.setText(rb.getString("3"));
        } else {
            try {
                boolean isValid = DAOUsers.checkUserLogin(userNameTextField.getText(), passwordTextField.getText());
                if (isValid) {

                    stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
                    stage.hide();

                    existingUpcomingAppointment();
                    loginActivitySuccessful();
                    stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
                    stage.setTitle("Home");
                    stage.setScene(new Scene(scene));
                    stage.show();
                } else {
                    loginActivityNotSuccessful ();
                    loginErrorLabel.setText(rb.getString("4"));
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method initializes Login screen and translates it to French if system is set to French language.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale locale = Locale.getDefault();
        rb = ResourceBundle.getBundle("language/Languages", locale);
        loginLabel.setText(rb.getString("loginRB"));
        passwordTextField.setPromptText(rb.getString("passwordTextRB"));
        userNameTextField.setPromptText(rb.getString("userNameTextRB"));
        loginButton.setText(rb.getString("loginButtonRB"));
        locationLabel.setText(rb.getString("la"));

        zoneLabel.setText(ZoneId.systemDefault().getId());
        passwordTextField.setFocusTraversable(false);
    }

}
