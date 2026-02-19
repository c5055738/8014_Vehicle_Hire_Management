/**
 * Represents a van vehicle in the Vehicle Hire Management System.
 * Vans require service every 5,000 miles and can be hired by customers aged 23 or over with a commercial license.
 * Vans rented for 10 or more days require inspection upon return.
 */
public final class Van extends AbstractVehicle{
    private boolean inspectionNeeded;

    /**
     * Constructs a new Van with the given vehicle ID.
     *
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
     *
     * @return the string "Van"
     */
    @Override
    public String getVehicleType(){
        return "Van";
    }

    /**
     * Returns the distance (in miles) after which this van requires service.
     *
     * @return 5000, the service interval for vans
     */
    @Override
    public int getDistanceRequirement(){
        return 5000;
    }

    /**
     * Indicates whether this van requires inspection.
     *
     * @return true if inspection is needed; false otherwise
     */
    public boolean getInspection(){
        return inspectionNeeded;
    }

    /**
     * Sets whether this van requires inspection.
     *
     * @param inspectionNeeded true to mark as requiring inspection; false otherwise
     */
    public void setInspection(boolean inspectionNeeded){
        this.inspectionNeeded = inspectionNeeded;
    }

    /**
     * Returns a string representation of this van including its vehicle ID and inspection status.
     *
     * @return a string in the format "Van" followed by the vehicle ID, with "(Requires Inspection)" appended if needed
     */
    @Override
    public String toString(){
        return "Van" + getVehicleID() + (inspectionNeeded ? "(Requires Inspection)" : "");
    }
}
