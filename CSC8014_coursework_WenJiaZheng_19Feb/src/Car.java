/**
 * Represents a car vehicle in the Vehicle Hire Management System.
 * Cars require service every 10,000 miles and can be hired by customers aged 18 or over.
 */
public final class Car extends AbstractVehicle {
	/**
	 * Constructs a new Car with the given vehicle ID.
	 *
	 * @param id the unique identifier for this car
	 */
	public Car(VehicleID id) {
        // Delegate to parent constructor
        super(id);
    }
    /**
     * Returns the vehicle type as "Car".
     *
     * @return the string "Car"
     */
    @Override
    public String getVehicleType() {
        return "Car";
    }

    /**
     * Returns the distance (in miles) after which this car requires service.
     *
     * @return 10000, the service interval for cars
     */
    @Override
    public int getDistanceRequirement(){
        return 10000;
    }

    /**
     * Returns a string representation of this car including its vehicle ID.
     *
     * @return a string in the format "Car" followed by the vehicle ID
     */
    @Override
    public String toString() {
        return "Car" + getVehicleID();
    }
}
