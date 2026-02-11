/**
 * The Name class is designed to be immutable,
 * this holds a customer’s name to ensuring it cannot be changed once created.
 */

public class Name {
    private final String firstName;
    private final String lastName;

    public Name(String fName, String lName) {
        if (fName == null || lName == null) {
            throw new NullPointerException("First and last names must be provided");
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
        Name comparedName = (Name) obj;
        return firstName.equals(comparedName.firstName) && lastName.equals(comparedName.lastName);
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

