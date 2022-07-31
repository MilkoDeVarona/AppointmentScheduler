package model;

/**
 * Customers model class.
 */
public class Customers {

    private int customerID, divisionID;
    private String customerName, address, postalCode, phoneNumber, country, division;

    /**
     * Customers class constructor. Instantiates a new customer object.
     * @param customerID
     * @param divisionID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param country
     * @param division
     */
    public Customers(int customerID, int divisionID,
                     String customerName, String address, String postalCode, String phoneNumber, String country, String division) {
        this.customerID = customerID;
        this.divisionID = divisionID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.division = division;
    }

    /**
     * Method gets Customer ID.
     * @return
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Method sets Customer ID.
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
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
     * Method gets customer Name.
     * @return
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Method sets customer Name.
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Method gets customer Address.
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * Method sets customer Address.
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Method gets customer Postal Code.
     * @return
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Method sets customers Postal Code.
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Method gets customer Phone Number.
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Method sets customer Phone Number.
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Method gets customer Country.
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     * Method sets customer country.
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Method gets Divisions.
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * Method sets Divisions.
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Method overrides toString method.
     * @return
     */
    @Override
    public String toString() {
        return customerID + " - " + customerName;
    }

}
