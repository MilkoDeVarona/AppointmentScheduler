package database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Contacts;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Appointments database class.
 */
public class DAOAppointments {

    /**
     * Method gets all existing appointments in database.
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointments> viewAllAppointments() throws SQLException {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID = c.Contact_ID ORDER BY Appointment_ID";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentsID = rs.getInt("Appointment_ID");
                int appointmentsCustomerID = rs.getInt("Customer_ID");
                int appointmentsUserID = rs.getInt("User_ID");
                int appointmentsContactID = rs.getInt("Contact_ID");
                String appointmentsTitle = rs.getString("Title");
                String appointmentsDescription = rs.getString("Description");
                String appointmentsLocation = rs.getString("Location");
                String appointmentsType = rs.getString("Type");
                String appointmentsContactName = rs.getString("Contact_Name");
                LocalDate appointmentsStartDate = rs.getDate("Start").toLocalDate();
                LocalDate appointmentsEndDate = rs.getDate("End").toLocalDate();
                LocalDateTime appointmentsStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appointmentsEnd = rs.getTimestamp("End").toLocalDateTime();
                Appointments a = new Appointments(appointmentsID, appointmentsCustomerID, appointmentsUserID, appointmentsContactID,
                        appointmentsTitle, appointmentsDescription, appointmentsLocation, appointmentsType, appointmentsContactName,
                        appointmentsStartDate, appointmentsEndDate, appointmentsStart, appointmentsEnd);
                appointmentsList.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsList;
    }

    /**
     * Method selects for last month's appointments.
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointments> viewAppointmentsByMonth() throws SQLException {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID = c.Contact_ID WHERE MONTH(Start) = MONTH(NOW()) ORDER BY Appointment_ID";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentsID = rs.getInt("Appointment_ID");
                int appointmentsCustomerID = rs.getInt("Customer_ID");
                int appointmentsUserID = rs.getInt("User_ID");
                int appointmentsContactID = rs.getInt("Contact_ID");
                String appointmentsTitle = rs.getString("Title");
                String appointmentsDescription = rs.getString("Description");
                String appointmentsLocation = rs.getString("Location");
                String appointmentsType = rs.getString("Type");
                String appointmentsContactName = rs.getString("Contact_Name");
                LocalDate appointmentsStartDate = rs.getDate("Start").toLocalDate();
                LocalDate appointmentsEndDate = rs.getDate("End").toLocalDate();
                LocalDateTime appointmentsStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appointmentsEnd = rs.getTimestamp("End").toLocalDateTime();
                Appointments a = new Appointments(appointmentsID, appointmentsCustomerID, appointmentsUserID, appointmentsContactID,
                        appointmentsTitle, appointmentsDescription, appointmentsLocation, appointmentsType, appointmentsContactName,
                        appointmentsStartDate, appointmentsEndDate, appointmentsStart, appointmentsEnd);
                appointmentsList.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsList;
    }

    /**
     * Method selects for last week's appointments.
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointments> viewAppointmentsByWeek() throws SQLException {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID = c.Contact_ID WHERE YEARWEEK(Start) = YEARWEEK(NOW()) ORDER BY Appointment_ID";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentsID = rs.getInt("Appointment_ID");
                int appointmentsCustomerID = rs.getInt("Customer_ID");
                int appointmentsUserID = rs.getInt("User_ID");
                int appointmentsContactID = rs.getInt("Contact_ID");
                String appointmentsTitle = rs.getString("Title");
                String appointmentsDescription = rs.getString("Description");
                String appointmentsLocation = rs.getString("Location");
                String appointmentsType = rs.getString("Type");
                String appointmentsContactName = rs.getString("Contact_Name");
                LocalDate appointmentsStartDate = rs.getDate("Start").toLocalDate();
                LocalDate appointmentsEndDate = rs.getDate("End").toLocalDate();
                LocalDateTime appointmentsStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appointmentsEnd = rs.getTimestamp("End").toLocalDateTime();
                Appointments a = new Appointments(appointmentsID, appointmentsCustomerID, appointmentsUserID, appointmentsContactID,
                        appointmentsTitle, appointmentsDescription, appointmentsLocation, appointmentsType, appointmentsContactName,
                        appointmentsStartDate, appointmentsEndDate, appointmentsStart, appointmentsEnd);
                appointmentsList.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsList;
    }

    /**
     * Method creates a new appointment in the database.
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactName
     * @return
     * @throws SQLException
     */
    public static boolean addAppointment (String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Integer customerID, Integer userID, String contactName) throws SQLException {
        Contacts selectedContact = DAOContacts.getContactID(contactName);
        try {
            String sql = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, selectedContact.getContactID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method updates appointment information in the database.
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactName
     * @param appointmentID
     * @throws SQLException
     */
    public static void updateAppointment (String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Integer customerID, Integer userID, String contactName, String appointmentID) throws SQLException {
        Contacts selectedContact = DAOContacts.getContactID(contactName);
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, selectedContact.getContactID());
            ps.setString(10, appointmentID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    /**
     * Method deletes an appointment from the database.
     * @param AppointmentID
     * @throws SQLException
     */
    public static void deleteAppointment (int AppointmentID) throws SQLException {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setInt(1, AppointmentID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method gets total number of appointments by type and month.
     * @param type
     * @param month
     * @return
     */
    public static int getTotalAppointmentsByTypeAndMonth (String type, String month) {
        int total = 0;
        try {
            String sql = "SELECT count(*) FROM appointments WHERE Type = ? AND MONTHNAME(Start) = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, type);
            ps.setString(2, month);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getInt("count(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

}
