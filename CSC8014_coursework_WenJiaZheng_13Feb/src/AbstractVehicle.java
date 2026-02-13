//尚未檢查
import java.util.Objects;

public abstract class AbstractVehicle implements Vehicle {
    private final VehicleID id;
    private int currentMileage;
    private boolean hired;
    protected AbstractVehicle(VehicleID id) {
        if (id == null) {
            throw new IllegalArgumentException("Vehicle ID can not be null");
        }
        this.id = id;
        this.currentMileage = 0;
        this.hired = false;
    }
    @Override
    public final VehicleID getVehicleID() {
        return id;
    }
    @Override
    public final int getCurrentMileage() {
        return currentMileage;
    }
    @Override
    public void setCurrentMileage(int currentMileage) {
        if (currentMileage < 0) {
            throw new IllegalArgumentException("Current mileage can not be negative");
        }
        this.currentMileage = currentMileage;
    }
    @Override
    public final boolean isHired() {
        return hired;
    }

    public void setHired(boolean hired){
        this.hired = hired;
    }

    @Override
    public final boolean performServiceIfDue(){
        if (this.currentMileage >= getDistanceRequirement()) {
            this.currentMileage = 0;
            return true;
        }
        return false;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof AbstractVehicle))
            return false;

        AbstractVehicle other = (AbstractVehicle) obj;
        return Objects.equals(this.id, other.id);
}

    @Override
    public int hashCode(){
            int hash = 17;
            int multiplier = 37;
            hash = hash*multiplier+ (id == null? 0 : id.hashCode());
            return hash;
        }
}

