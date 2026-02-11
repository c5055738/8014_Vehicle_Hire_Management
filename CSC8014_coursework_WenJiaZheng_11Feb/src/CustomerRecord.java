/**
 * Represents a customer's record.
 * The class is designed to be immutable so its data cannot be modified.
 */

import java.util.Date;

public final class CustomerRecord {
    private final Name name;
    private final Date dateOfBirth;
    private final boolean commercialLicense;
    // Assume the customer ID is assigned by VehicleManager, reserved here for now
    private final String customerID;

    public CustomerRecord(Name name, Date dob, boolean commercialLicense, String customerID) {
        // 1. Defensive checks (use the parameter 'dob', not the field)
        if (name == null) {
            throw new NullPointerException("First and last names must be provided");
        }
        if (dob == null || dob.after(new Date())) {
            throw new IllegalArgumentException("Date of birth is invalid");
        }
        if (customerID == null || customerID.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer number cannot be empty");
        }

        this.name = name;
        this.dateOfBirth = new Date(dob.getTime());
        this.commercialLicense = commercialLicense;
        this.customerID = customerID;
    }

    public Name getName() {
        return name;
    }

    public Date getDateOfBirth() {
        // Return a defensive copy to prevent external modification
        return new Date(dateOfBirth.getTime());
    }

    public boolean hasCommercialLicense() {
        return commercialLicense;
    }
    public String getCustomerID() {
        return customerID;
    }
}