import java.util.Date;

/**
 * This class represents a customer in the Hire Management System.
 * It stores the customer's name, date of birth, commercial license status, and unique ID.
 */

public final class CustomerRecord {
    private final Name name;
    private final Date birthDate;
    private final boolean commercialLicense;
    private final String customerID;

    /**
     * Constructs a new CustomerRecord.
     * @param name the customer's name
     * @param dob the date of birth
     * @param commLicense whether the customer has a commercial license
     * @param ID the unique customer identifier
     * @throws NullPointerException if name or dob is null
     * @throws IllegalArgumentException if ID is null or empty
     */
    public CustomerRecord(Name name, Date dob, boolean commLicense, String ID) {
        // Validate required non-null fields
        if (name == null || dob == null) {
            throw new NullPointerException("Names and Date of Birth can not be null");
        }

        if (ID == null || ID.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer number cannot be empty");
        }

        this.name = name;
        // Defensive copy of date to preserve immutability
        this.birthDate = new Date(dob.getTime());
        this.commercialLicense = commLicense;
        this.customerID = ID;
    }

    /**
     * Returns the customer's name.
     * @return the Name object
     */
    public Name getName() {
        return name;
    }

    /**
     * Returns the customer's date of birth.
     * @return the date of birth
     */
    public Date getDateOfBirth() {
        return new Date(birthDate.getTime());
    }

    /**
     * Indicates whether the customer has a commercial license.
     * @return true if the customer has a commercial license, false otherwise
     */
    public boolean hasCommercialLicense() {
        return commercialLicense;
    }

    /**
     * Returns the customer ID.
     * @return the customer ID
     */
    public String getCustomerID() {
        return customerID;
    }
}