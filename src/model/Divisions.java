package model;

/**
 * Divisions model class.
 */
public class Divisions {

    private int divisionID, countryID;
    private String division;

    /**
     * Divisions class constructor. Instantiates a new divisions object.
     * @param divisionID
     * @param countryID
     * @param division
     */
    public Divisions (int divisionID, int countryID, String division) {
        this.divisionID = divisionID;
        this.countryID = countryID;
        this.division = division;
    }

    /**
     * Method gets Division ID.
     * @return
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Method sets Division ID.
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Method gets Country ID.
     * @return
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Method sets Country ID.
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Method gets Division.
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * Method sets Division.
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

}
