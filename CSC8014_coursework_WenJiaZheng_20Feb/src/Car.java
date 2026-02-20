/**
 * This class represents a car in the Hire Management System.
 * Cars require service every 10,000 miles and can be hired by customers aged 18 or over.
 */

public final class Car extends AbstractVehicle {
	/**
	 * Constructs a new Car with the given vehicle ID.
	 * @param id the unique identifier for this car
	 */
	public Car(VehicleID id) {
        // Pass the ID to the superclass
        super(id);
    }
    /**
     * Returns the vehicle type as "Car".
     * @return the string "Car"
     */
    @Override
    public String getVehicleType() {
        return "Car";
    }

    /**
     * Return the service mileage for cars
     * @return 10000 miles
     *
     */
    @Override
    public int getDistanceRequirement(){
        return 10000;
    }

    /**
     * Returns the car's type and ID as a string
     * @return the "Car" and the vehicle's ID
     */
    @Override
    public String toString() {

        return "Car" + getVehicleID();
    }
}
