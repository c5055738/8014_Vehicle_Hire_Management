public abstract class AbstractVehicle implements Vehicle {
    private final VehicleID vehicleID;
    private int currentMileage;
    private boolean isHired;

    protected AbstractVehicle(VehicleID id) {
        this.vehicleID = id;
        this.currentMileage = 0;
        this.isHired = false;
    }

    @Override
    public VehicleID getVehicleID() { return vehicleID; }

    @Override
    public int getCurrentMileage() { return currentMileage; }

    @Override
    public void setCurrentMileage(int mileage) {
        if (mileage < 0) throw new IllegalArgumentException("里程不能為負數");
        this.currentMileage = mileage;
    }

    @Override
    public boolean isHired() { return isHired; }

    // 設定租借狀態（內部或子類別使用）
    protected void setHired(boolean hired) { this.isHired = hired; }
}