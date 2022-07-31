package model;

/**
 * Countries model class.
 */
public class Countries {

    private int countryID;
    private String countryName;

    /**
     * Countries class constructor. Instantiates a new country object.
     * @param countryID
     * @param countryName
     */
    public Countries(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * Method gets Country Id
     * @return
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Method gets Country Name.
     * @return
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Method overrides toString method.
     * @return
     */
    @Override
    public String toString() {
        return countryName;
    }

}
