package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DAOCustomers {

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
    }

    // Delete
    public static int delete (int fruitID) throws SQLException {
        String sql = "DELETE FROM FRUITS WHERE Fruit_Id = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, fruitID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /******************************************************************************/

    // Read
    public static void select () throws SQLException {
        String sql = "SELECT * FROM FRUITS";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int fruitID = rs.getInt("Fruit_ID");
            String fruitName = rs.getString("Fruit_Name");
            System.out.println(fruitID + " | " + fruitName);
        }
    }

    // Read
    public static void select (int colorID) throws SQLException {
        String sql = "SELECT * FROM FRUITS WHERE Color_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, colorID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int fruitID = rs.getInt("Fruit_ID");
            String fruitName = rs.getString("Fruit_Name");
            int colorIDFK = rs.getInt("Color_ID");
            System.out.println(fruitID + " | " + fruitName + " | " + colorIDFK);
        }
    }

}
