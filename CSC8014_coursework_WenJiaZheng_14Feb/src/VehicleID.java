/**
 * This class is designed to be immutable.
 */
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public final class VehicleID {
    private static final Map<String, VehicleID> all_IDs = new HashMap<>();
    private final String id;

    private VehicleID (String id){
        this.id = id;
    }
    public static VehicleID getInstance (String type){
    Random r = new Random();
    String generatedID;

    do{
        String Char1 = type.equals("Car") ? "C" : "V";
        String Char2 = "" + (char)('A'+ r.nextInt(26)) + r.nextInt(10);
        int number = r.nextInt(900) + 100;

        if (type.equals("Car")){
            if (number % 2 !=0) number++;
        }else {
            if (number % 2 == 0) number++;
        }

        generatedID = Char1 + Char2 + "-" + String. format("%03d", number);
    }
    while (all_IDs.containsKey(generatedID));
    VehicleID newID = new VehicleID(generatedID);
    all_IDs.put(generatedID, newID);
    return newID;
}
@Override
public String toString(){
    return id;
    }
@Override
        public boolean equals(Object obj){
        if (this == obj) return true;
        if (!(obj instanceof VehicleID)) return false;
        VehicleID that = (VehicleID)obj;
        return id.equals(that.id);
    }
@Override
public int hashCode(){
    return id.hashCode();
}
}