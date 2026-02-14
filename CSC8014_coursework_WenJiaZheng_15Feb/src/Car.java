public final class Car extends AbstractVehicle {
	public Car(VehicleID id) {
        super(id);
    }
    @Override
    public String getVehicleType() {
        return "Car";
    }

    @Override
    public int getDistanceRequirement(){
        return 10000;
    }

    @Override
    public String toString() {
        return "Car" + getVehicleID();
    }
}
