import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

/**
 * Immutable unique identifier for a vehicle.
 * Format: single letter (C for car, V for van) + random letter/digit + hyphen + 3-digit number.
 * Car IDs use even numbers; van IDs use odd numbers.
 */
public final class VehicleID implements Comparable<VehicleID> {
    private static final Map<String, VehicleID> all_IDs = new HashMap<>();
    private static final Random r = new Random();

    private final String id;

    /**
     * Private constructor. Use getInstance to create VehicleID instances.
     *
     * @param id the internal string representation of the ID
     */
    private VehicleID(String id) {
        this.id = id;
    }

    /**
     * Creates and returns a new unique VehicleID for the given vehicle type.
     *
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
     * Compares this VehicleID to another for ordering.
     *
     * @param o the other VehicleID to compare
     * @return negative, zero, or positive as this is less than, equal to, or greater than o
     */
    @Override
    public int compareTo(VehicleID o) {
        return this.id.compareTo(o.id);
    }

    /**
     * Returns the string representation of this ID.
     *
     * @return the ID string (e.g. "C_A1-123")
     */
    @Override
    public String toString() {
        return id;
    }

    /**
     * Compares this VehicleID to another object for equality.
     *
     * @param obj the object to compare
     * @return true if equal; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof VehicleID)) return false;
        VehicleID that = (VehicleID) obj;
        return id.equals(that.id);
    }

    /**
     * Returns the hash code based on the ID string.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Returns the first component of the ID (before the hyphen).
     *
     * @return the prefix part (e.g. "CA1")
     */
    public String firstComponent() {
        return id.split("-")[0];
    }

    /**
     * Returns the second component of the ID (after the hyphen).
     *
     * @return the numeric part (e.g. "123")
     */
    public String secondComponent() {
        return id.split("-")[1];
    }
}

