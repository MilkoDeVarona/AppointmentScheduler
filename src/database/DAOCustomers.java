package database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOCustomers {

    // Get customer info to populate table on the Customers screen
    public static ObservableList<Customers> getAllCustomers () throws SQLException{
        ObservableList<Customers> customerList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customers AS c INNER JOIN first_level_divisions AS fld ON c.Division_ID = fld.Division_ID INNER JOIN countries AS ctr ON ctr.Country_ID = fld.COUNTRY_ID";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                int divisionID = rs.getInt("Division_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhoneNumber = rs.getString("Phone");
                String customerCountry = rs. getString("Country");
                String customerDivision = rs.getString("Division");
                Customers c = new Customers(customerID, divisionID, customerName, customerAddress, customerPostalCode, customerPhoneNumber, customerCountry, customerDivision);
                customerList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    // Create a new customer in the database
    public static void addCustomer (String customerName, String customerAddress, String customerPostalCode, String customerPhone, int customerDivision) throws SQLException {
        try {
            String sql = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, customerAddress);
            ps.setString(3, customerPostalCode);
            ps.setString(4, customerPhone);
            ps.setInt(5, customerDivision);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update customer info in the database
    public static void updateCustomer (String customerName, String customerAddress, String customerPostalCode, String customerPhone, int customerDivision, int customerID) throws SQLException {
        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, customerAddress);
            ps.setString(3, customerPostalCode);
            ps.setString(4, customerPhone);
            ps.setInt(5, customerDivision);
            ps.setInt(6, customerID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a customer from the database
    public static void deleteCustomer (int CustomerID) throws SQLException {
        try {
            String sql = "DELETE FROM customers WHERE Customer_Id = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setInt(1, CustomerID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /******************************************************************************/

    /*
    // Create
    public static int insert (String fruitName, int colorID) throws SQLException {
        String sql = "INSERT INTO FRUITS (Fruit_Name, Color_ID) VALUES (?, ?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, fruitName);
        ps.setInt(2, colorID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    // Update
    public static int update (int fruitID, String fruitName) throws SQLException {
        String sql = "UPDATE FRUITS SET FRUIT_NAME = ? WHERE Fruit_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, fruitName);
        ps.setInt(2, fruitID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }*/

}
