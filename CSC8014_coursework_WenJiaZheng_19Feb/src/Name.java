/**
 * Immutable representation of a person's name (first and last name).
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
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Compares this Name to another object for equality.
     *
     * @param obj the object to compare
     * @return true if both first and last names match; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Name)) return false;
        Name otherName = (Name) obj;
        return firstName.equals(otherName.firstName) && lastName.equals(otherName.lastName);
    }

    /**
     * Returns the hash code based on first and last names.
     *
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
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}

