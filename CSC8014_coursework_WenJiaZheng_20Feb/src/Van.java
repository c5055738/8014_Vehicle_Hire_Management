/**
 * This class represents a van in the Hire Management System.
 * Vans require service every 5,000 miles and can be hired by customers aged 23 or over with a commercial license.
 * Vans rented for 10 or more days require inspection when return.
 */
public final class Van extends AbstractVehicle{
    private boolean inspectionNeeded;

    /**
     * Constructs a new Van with the given vehicle ID.
     * @param id the unique identifier for this van
     */
    public Van (VehicleID id){
        // Delegate to parent constructor
        super(id);
        // Initially no inspection is needed
        this. inspectionNeeded = false;
    }

    /**
     * Returns the vehicle type as "Van".
     * @return the string "Van"
     *
     */
    @Override
    public String getVehicleType(){
        return "Van";
    }

    /**
     * Return the service mileage for vans
     * @return 5000 miles
     */
    @Override
    public int getDistanceRequirement(){
        return 5000;
    }

    /**
     * Returns whether this van requires inspection.
     * @return true if inspection is needed; false otherwise
     */
    public boolean getInspection(){
        return inspectionNeeded;
    }

    /**
     * Sets whether this van requires inspection.
     * @param inspectionNeeded true if inspection is required; false otherwise
     */
    public void setInspection(boolean inspectionNeeded){
        this.inspectionNeeded = inspectionNeeded;
    }

    /**
     * Returns the van's type and ID as a string.
     * @return the "Van" and the vehicle's ID, with "(Requires Inspection)" if applicable
     */
    @Override
    public String toString(){
        return "Van" + getVehicleID() + (inspectionNeeded ? "(Requires Inspection)" : "");
    }
}
