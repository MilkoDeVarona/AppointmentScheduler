package database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import model.Divisions;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Divisions database class.
 */
public class DAODivisions {

    /**
     * Method gets all divisions and corresponding countries.
     * @return
     * @throws SQLException
     */
    public static ObservableList <Divisions> getAllDivisions () throws SQLException {
        ObservableList <Divisions> divisionsList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                int countryID = rs.getInt("Country_ID");
                String division = rs.getString("Division");
                Divisions d = new Divisions(divisionID, countryID, division);
                divisionsList.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionsList;
    }

    /**
     * Method gets divisions by country.
     * @param country
     * @return
     * @throws SQLException
     */
    public static ObservableList <Divisions> getDivisionByCountry (String country) throws SQLException {
        Countries chosenCountry = DAOCountries.getCountryByName(country);
        ObservableList <Divisions> divisionsList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_Id = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setInt(1, chosenCountry.getCountryID());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                int countryID = rs.getInt("Country_ID");
                String division = rs.getString("Division");
                //System.out.println(countryID + " | " + divisionID + " | " + division);
                Divisions d = new Divisions(divisionID, countryID, division);
                divisionsList.add(d);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionsList;
    }

    /**
     * Method gets a division by name.
     * @param oneDivision
     * @throws SQLException
     */
    public static void getDivisionByName (String oneDivision) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, oneDivision);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            int countryID = rs.getInt("Country_ID");
            String division = rs.getString("Division");
            // System.out.println(countryID + " | " + divisionID + " | " + division);
        }
    }

    /**
     * Method gets divisions by country id.
     * @param countryID
     * @throws SQLException
     */
    public static void getDivisionByCountryID (int countryID) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, countryID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            int countriesID = rs.getInt("Country_ID");
            String division = rs.getString("Division");
            System.out.println(countriesID + " | " + divisionID + " | " + division);
        }
    }

    /**
     * Method gets a division by division id.
     * @param divisionByID
     * @return
     * @throws SQLException
     */
    public static Divisions getDivisionID (String divisionByID) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, divisionByID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Divisions selectedDivision = new Divisions(
                    rs.getInt("Division_ID"),
                    rs.getInt("Country_ID"),
                    rs.getString("Division")
            );
            return selectedDivision;
        }
        return null;
    }

}
