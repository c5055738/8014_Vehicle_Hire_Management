/**
 * VehicleManager  
 *
 * @author Rouaa Yassin Kassab
 * Copyright (C) 2026 Newcastle University, UK
 */
import java.util.*;
public class VehicleManager {
    private final Map<VehicleID, Vehicle> vehicles;
    private final Map<Integer, CustomerRecord> customers;
    private final Map<Integer, Set<Vehicle>> vehiclesOnRent;

    public VehicleManager() {
        vehicles = new HashMap<>();
        customers = new HashMap<>();
        vehiclesOnRent = new HashMap<>();
    }

	public Vehicle addVehicle(String vehicleType){
        Vehicle newVehicle;
        VehicleID id = VehicleID.getInstance(vehicleType);
        if(vehicleType.equalsIgnoreCase("car")){
            newVehicle = new Car(id);
        }
        else if (vehicleType.equalsIgnoreCase("van")){
            newVehicle = new Van(id);
        }
        else{
            throw new IllegalArgumentException("Invalid vehicle type: " + vehicleType);
        }
        vehicles.put(id, newVehicle);
        return newVehicle;
	}

	public int noOfAvailableVehicles(String vehicleType) {
        int count = 0;

        for(Vehicle v : vehicles.values()){
            boolean isValidType =  v.getVehicleType().equalsIgnoreCase(vehicleType);
            boolean isNotHired = !v.isHired();
            if(isValidType && isNotHired){
                count ++;
            }
        }
        return count;
	}

	public CustomerRecord addCustomerRecord(String firstName, String lastName, Date dob, Boolean hasCommercialLicense) 
	{
		//add your code here. Do NOT change the method signature 
		return null; 
	}



	public boolean hireVehicle(CustomerRecord customerRecord, String vehicleType, int duration) {
		//add your code here. Do NOT change the method signature 
		return null; 
	}



	public void returnVehicle(VehicleID vehicleID ,CustomerRecord customerRecord, int mileage) {		
		//add your code here. Do NOT change the method signature
	}	


	public Collection<Vehicle> getVechilesByCustomer (CustomerRecord customerRecord){ 
		//add your code here. Do NOT change the method signature
		return null;
	}




}
