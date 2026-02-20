/**
 * This class represents a name in the Hire Management System.
 * It stores a first name and a last name.
 */

public final class Name {
    private final String firstName;
    private final String lastName;

    /**
     * Constructs a Name with the given first and last names.
     *
     * @param fName the first name
     * @param lName the last name
     * @throws NullPointerException if either name is null
     */
    public Name(String fName, String lName) {
        if (fName == null || lName == null) {
            throw new NullPointerException("First and last names can not be null");
        }
        this.firstName = fName;
        this.lastName = lName;
    }

    /**
     * Returns the first name.
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name.
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Compares this Name with another object.
     * @param obj the object to compare
     * @return true if both first and last names match, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Name)) return false;
        Name otherName = (Name) obj;
        return firstName.equals(otherName.firstName) && lastName.equals(otherName.lastName);
    }

    /**
     * Returns the hash code for this name.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = firstName.hashCode();
        hash = 31 * hash + lastName.hashCode();
        return hash;
    }

    /**
     * Returns the full name as "firstName lastName".
     * @return the full name as a string
     */
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}

