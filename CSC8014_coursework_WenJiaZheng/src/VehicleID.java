import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

/**
 * This class represents a unique identifier for a vehicle in the Hire Management System.
 * It generates and stores vehicle IDs for cars and vans.
 */
public final class VehicleID implements Comparable<VehicleID> {
    private static final Map<String, VehicleID> all_IDs = new HashMap<>();
    private static final Random r = new Random();

    private final String id;

    /**
     * Constructs a VehicleID with the given ID string.
     * @param id the internal string representation of the ID
     */
    private VehicleID(String id) {
        this.id = id;
    }

    /**
     * Creates and returns a new unique VehicleID for the given vehicle type.
     * @param type the vehicle type ("car" or "van")
     * @return a new unique VehicleID
     * @throws IllegalArgumentException if type is null or not "car"/"van"
     */
    public static VehicleID getInstance(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null");
        }

        String t = type.trim().toLowerCase();
        if (!t.equals("car") && !t.equals("van")) {
            throw new IllegalArgumentException("Invalid vehicle type: " + type);
        }

        String generatedID;

        do {
            // Prefix: C for car, V for van
            String char1 = t.equals("car") ? "C" : "V";
            String char2 = "" + (char) ('A' + r.nextInt(26)) + r.nextInt(10);

            // Generate 3-digit number in range 100-999
            int number = r.nextInt(900) + 100;

            // Car IDs must end with even number; van IDs with odd number
            if (t.equals("car")) {
                if (number % 2 != 0) number++;
                if (number > 999) number -= 2; // Keep three digits
            } else {
                if (number % 2 == 0) number++;
                if (number > 999) number -= 2;
            }

            generatedID = char1 + char2 + "-" + String.format("%03d", number);

        } while (all_IDs.containsKey(generatedID));

        VehicleID newID = new VehicleID(generatedID);
        all_IDs.put(generatedID, newID);
        return newID;
    }

    /**
     * Compares this VehicleID with another.
     * @param o the other VehicleID to compare
     * @return a negative, zero, or positive value based on ordering
     */
    @Override
    public int compareTo(VehicleID o) {
        return this.id.compareTo(o.id);
    }

    /**
     * Returns the ID as a string.
     * @return the ID string
     */
    @Override
    public String toString() {
        return id;
    }

    /**
     * Compares this VehicleID with another object.
     * @param obj the object to compare
     * @return true if both IDs are equal; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof VehicleID)) return false;
        VehicleID that = (VehicleID) obj;
        return id.equals(that.id);
    }

    /**
     * Returns the hash code for this ID.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Returns the first component of the ID (before the hyphen).
     * @return the prefix before the hyphen
     */
    public String firstComponent() {
        return id.split("-")[0];
    }

    /**
     * Returns the second part of the ID.
     * @return the part after the hyphen
     */
    public String secondComponent() {
        return id.split("-")[1];
    }
}

