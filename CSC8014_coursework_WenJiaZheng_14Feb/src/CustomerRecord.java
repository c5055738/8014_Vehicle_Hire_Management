/**
 * This class is designed to be immutable.
 */
import java.util.Date;

public final class CustomerRecord {
    private final Name name;
    private final Date birthDate;
    private final boolean commercialLicense;
    private final String customerID;

    public CustomerRecord(Name name, Date dob, boolean commLicense, String ID) {
        if (name == null || dob == null) {
            throw new NullPointerException("Names and Date of Birth can not be null");
        }

        if (ID == null || ID.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer number cannot be empty");
        }

        this.name = name;
        this.birthDate = new Date(dob.getTime());
        this.commercialLicense = commLicense;
        this.customerID = ID;
    }

    public Name getName() {
        return name;
    }
    public Date getDateOfBirth() {
        return new Date(birthDate.getTime());
    }
    public boolean hasCommercialLicense() {
        return commercialLicense;
    }
    public String getCustomerID() {
        return customerID;
    }
}