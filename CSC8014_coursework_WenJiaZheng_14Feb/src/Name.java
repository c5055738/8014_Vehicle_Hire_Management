/**
 * This class is designed to be immutable.
 */
public final class Name {
    private final String firstName;
    private final String lastName;

    public Name(String fName, String lName) {
        if (fName == null || lName == null) {
            throw new NullPointerException("First and last names can not be null");
        }
        this.firstName = fName;
        this.lastName = lName;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Name)) return false;
        Name otherName = (Name) obj;
        return firstName.equals(otherName.firstName) && lastName.equals(otherName.lastName);
    }

    @Override
    public int hashCode() {
        int hash = firstName.hashCode();
        hash = 31 * hash + lastName.hashCode();
        return hash;
    }
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}

