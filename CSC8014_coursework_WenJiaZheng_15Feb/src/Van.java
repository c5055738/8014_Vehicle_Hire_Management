public final class Van extends AbstractVehicle{
    private boolean inspectionNeeded;

    public Van (VehicleID id){
        super(id);
        this. inspectionNeeded = false;
    }

    @Override
    public String getVehicleType(){
        return "Van";
    }

    @Override
    public int getDistanceRequirement(){
        return 5000;
    }
    public boolean getInspection(){
        return inspectionNeeded;
    }
    public void setInspection(boolean inspectionNeeded){
        this.inspectionNeeded = inspectionNeeded;
    }

    @Override
    public String toString(){
        return "Van" + getVehicleID() + (inspectionNeeded ? "(Requires Inspection)" : "");
    }
}
