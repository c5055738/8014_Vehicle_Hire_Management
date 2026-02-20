import java.util.Objects;

/**
 * This class represents a vehicle in the Hire Management System.
 * Provides common vehicle properties and behaviour.
 */

public abstract class AbstractVehicle implements Vehicle {
    private final VehicleID id;
    private int currentMileage;
    private boolean hired;

    /**
     * Constructs an abstract vehicle with the given ID.
     * @param id the unique identifier for this vehicle
     * @throws IllegalArgumentException if id is null
     */
    protected AbstractVehicle(VehicleID id) {
        // Validate that ID is not null
        if (id == null) {
            throw new IllegalArgumentException("Vehicle ID cannot be null");
        }
        this.id = id;
        this.currentMileage = 0;
        this.hired = false;
    }

    /**
     * Returns the vehicle ID.
     * @return the VehicleID of this vehicle
     */
    @Override
    public final VehicleID getVehicleID() {
        return id;
    }

    /**
     * Returns the current mileage since the last service.
     * @return the current mileage in miles
     */
    @Override
    public final int getCurrentMileage() {
        return currentMileage;
    }

    /**
     * Sets the current mileage of this vehicle.
     * @param currentMileage the new mileage value
     * @throws IllegalArgumentException if currentMileage is negative
     */
    @Override
    public void setCurrentMileage(int currentMileage) {
        // Validate non-negative mileage
        if (currentMileage < 0) {
            throw new IllegalArgumentException("Current mileage can not be negative");
        }
        this.currentMileage = currentMileage;
    }

    /**
     * Indicates whether this vehicle is currently hired.
     * @return true if hired, false otherwise
     */
    @Override
    public final boolean isHired() {
        return hired;
    }

    /**
     * Sets the hire status of this vehicle.
     * @param hired true if the vehicle is hired, false otherwise
     */
    @Override
    public void setHired(boolean hired){
        this.hired = hired;
    }

    /**
     * Checks if service is due and performs it if required.
     * Resets mileage to 0 when service is performed.
     * @return true if service was performed, false otherwise
     */
    @Override
    public final boolean performServiceIfDue(){
        // Check if mileage has reached the service threshold
        if (this.currentMileage >= getDistanceRequirement()) {
            this.currentMileage = 0;
            return true;
        }
        return false;
    }

    /**
     * Compares this vehicle with another object.
     * Two vehicles are equal if they have the same vehicle ID.
     * @param obj the object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof AbstractVehicle))
            return false;

        AbstractVehicle other = (AbstractVehicle) obj;
        return Objects.equals(this.id, other.id);
}

    /**
     * Returns the hash code for this vehicle.
     * @return the hash code
     */
    @Override
    public int hashCode(){
            int hash = 17;
            int multiplier = 37;
            hash = hash*multiplier+ (id == null? 0 : id.hashCode());
            return hash;
        }
}

