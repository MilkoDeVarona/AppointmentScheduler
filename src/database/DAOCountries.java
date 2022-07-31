package database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Countries database class.
 */
public class DAOCountries {

    /**
     * Method gets all countries in the database.
     * @return
     * @throws SQLException
     */
    public static ObservableList<Countries> getAllCountries () throws SQLException {
        ObservableList <Countries> countriesList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM countries ORDER BY Country_ID";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                Countries ctr = new Countries (countryID, country);
                countriesList.add(ctr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countriesList;
    }

    /**
     * Method gets a country by name.
     * @param Country
     * @return
     * @throws SQLException
     */
    public static Countries getCountryByName (String Country) throws SQLException {
        String sql = "SELECT * FROM countries WHERE Country = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, Country);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            //System.out.println(countryID + " | " + countryName);
            return new Countries(countryID, countryName);
        }
        return null;
    }

}
