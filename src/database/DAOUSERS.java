package database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOUSERS {

    // Method gets all users
    public static ObservableList<Users> getAllUsers() {
        ObservableList<Users> usersList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM users ORDER BY User_ID";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");
                Users usr = new Users(userID, userName, userPassword);
                usersList.add(usr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    // Method check if user and pass combination is valid
    public static boolean checkUserLogin(String userN, String userP) {
        try {
            String sql = "SELECT * FROM users WHERE User_Name = ? AND PASSWORD = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, userN);
            ps.setString(2, userP);
            ResultSet rs = ps.executeQuery();
            return (rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
