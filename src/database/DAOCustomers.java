package database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;
import model.Divisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Customers database class.
 */
public class DAOCustomers {

    /**
     * Method gets all customers in the database and associated divisions.
     * @return
     * @throws SQLException
     */
    public static ObservableList<Customers> getAllCustomers () throws SQLException{
        ObservableList<Customers> customerList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customers AS c INNER JOIN first_level_divisions AS fld ON c.Division_ID = fld.Division_ID INNER JOIN countries AS ctr ON ctr.Country_ID = fld.COUNTRY_ID ORDER BY Customer_ID";
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

    /**
     * Method creates a new customer in the database.
     * @param customerName
     * @param customerAddress
     * @param customerPostalCode
     * @param customerPhone
     * @param customerDivision
     * @return
     * @throws SQLException
     */
    public static boolean addCustomer (String customerName, String customerAddress, String customerPostalCode, String customerPhone, String customerDivision) throws SQLException {
        Divisions selectedDivision = DAODivisions.getDivisionID(customerDivision);
        try {
            String sql = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, customerAddress);
            ps.setString(3, customerPostalCode);
            ps.setString(4, customerPhone);
            ps.setInt(5, selectedDivision.getDivisionID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method updates customer information in the database
     * @param customerName
     * @param customerAddress
     * @param customerPostalCode
     * @param customerPhone
     * @param customerDivision
     * @param customerID
     * @return
     * @throws SQLException
     */
    public static boolean updateCustomer (String customerName, String customerAddress, String customerPostalCode, String customerPhone, String  customerDivision, int customerID) throws SQLException {
        Divisions selectedDivision = DAODivisions.getDivisionID(customerDivision);
        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, customerAddress);
            ps.setString(3, customerPostalCode);
            ps.setString(4, customerPhone);
            ps.setInt(5, selectedDivision.getDivisionID());
            ps.setInt(6, customerID);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method deletes selected customer and associated appointments from the database.
     * @param CustomerID
     * @throws SQLException
     */
    public static void deleteCustomer (int CustomerID) throws SQLException {
        try {
            String sqlA = "DELETE FROM appointments WHERE Customer_ID = ?";
            PreparedStatement psA = DBConnection.connection.prepareStatement(sqlA);
            psA.setInt(1, CustomerID);
            psA.executeUpdate();

            String sqlC = "DELETE FROM customers WHERE Customer_Id = ?";
            PreparedStatement psC = DBConnection.connection.prepareStatement(sqlC);
            psC.setInt(1, CustomerID);
            psC.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method gets total number of customers by country.
     * @param country
     * @return
     */
    public static int getTotalCustomersByCountry (String country) {
        int total = 0;
        try {
            String sql = "SELECT count(*) FROM customers AS c INNER JOIN first_level_divisions AS fld ON c.Division_ID = fld.Division_ID INNER JOIN countries AS ctr ON ctr.Country_ID = fld.COUNTRY_ID WHERE Country = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, country);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getInt("count(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * Method gets divisions by country.
     * @param country
     * @return
     * @throws SQLException
     */
    public static ObservableList<String> getDivisionsByCountry (String country) throws SQLException{
        ObservableList<String> divisionsList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT c.Country, c.Country_ID,  d.Division_ID, d.Division FROM countries as c RIGHT OUTER JOIN first_level_divisions AS d ON c.Country_ID = d.Country_ID WHERE c.Country = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, country);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                divisionsList.add(rs.getString("Division"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionsList;
    }

}
