import java.util.Map;
import java.util.HashMap;
import java.util.Random;

/**
 * This class is designed to be immutable.
 */
public final class VehicleID implements Comparable<VehicleID> {
    private static final Map<String, VehicleID> all_IDs = new HashMap<>();
    private static final Random r = new Random();

    private final String id;

    private VehicleID(String id) {
        this.id = id;
    }

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
            String char1 = t.equals("car") ? "C" : "V";
            String char2 = "" + (char) ('A' + r.nextInt(26)) + r.nextInt(10);

            int number = r.nextInt(900) + 100; // 100-999

            // car -> even, van -> odd
            if (t.equals("car")) {
                if (number % 2 != 0) number++;
                if (number > 999) number -= 2; // 保持三位數
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

    @Override
    public int compareTo(VehicleID o) {
        return this.id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof VehicleID)) return false;
        VehicleID that = (VehicleID) obj;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}