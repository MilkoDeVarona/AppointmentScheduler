package database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Contacts;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOContacts {

    // Get all contacts
    public static ObservableList<Contacts> getAllContacts() throws SQLException {
        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                Contacts apt = new Contacts(contactID, contactName, contactEmail);
                contactsList.add(apt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactsList;
    }

    // Get contacts by ID
    public static Contacts getContactID (String contactName) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE Contact_Name = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int contID = rs.getInt("Contact_ID");
            String contName = rs.getString("Contact_Name");
            String contEmail = rs.getString("Email");
            return new Contacts(contID, contName, contEmail);
        }
        return null;
    }

}
