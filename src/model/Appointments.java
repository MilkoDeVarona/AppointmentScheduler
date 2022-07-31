package model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *Appointments model class.
 */
public class Appointments {

    private int appointmentID, customerID, userID, contactID;
    private String title, description, location, type, contactName;
    private LocalDate startDate, endDate;
    private LocalDateTime start, end;

    /**
     * Appointment class constructor. Instantiates new appointment object.
     * @param appointmentID
     * @param customerID
     * @param userID
     * @param contactID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param contactName
     * @param startDate
     * @param endDate
     * @param start
     * @param end
     */
    public Appointments(int appointmentID, int customerID, int userID, int contactID,
                        String title, String description, String location, String type, String contactName,
                        LocalDate startDate, LocalDate endDate,
                        LocalDateTime start, LocalDateTime end) {
        this.appointmentID = appointmentID;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contactName = contactName;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.start = start;
        this.end = end;
    }

    /**
     * Method gets Appointments ID.
     * @return
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Method sets Appointment ID.
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
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
     * Method gets User ID.
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Method sets User ID.
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
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
     * Method gets appointment Title.
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method sets appointment Title.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Method gets appointment Description.
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method sets appointment Description.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method gets appointment Location.
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * Method sets appointment Location.
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Method gets appointment Type.
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Method sets appointment Type.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Method gets Contact name.
     * @return
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Method sets Contact Name.
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Method gets appointment Start Date.
     * @return
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Method sets appointment Start Date.
     * @param startDate
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Method gets appointment End Date.
     * @return
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Method sets appointment End date.
     * @param endDate
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Method gets appointment Start date and time.
     * @return
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Method sets appointment Start date and time.
     * @param start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Method gets appointment End date and time.
     * @return
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Method sets appointment End date and time.
     * @param end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

}
