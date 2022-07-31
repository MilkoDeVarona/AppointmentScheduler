package model;

/**
 * Contacts model class.
 */
public class Contacts {

    private int contactID;
    private String contactName, contactEmail;

    /**
     * Contacts class constructor. Instantiates a new contact object.
     * @param contactID
     * @param contactName
     * @param contactEmail
     */
    public Contacts(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Method gets Contact ID.
     * @return
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Method sets Contact ID.
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Method gets Contact name.
     * @return
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Method sets Contact name.
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Method gets Contact Email.
     * @return
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Method sets Contact Email.
     * @param contactEmail
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * Method overrides toString method.
     * @return
     */
    @Override
    public String toString() {
        return contactID + " - " + contactName;
    }

}
