import java.util.*;

public class VehicleManager {
    private final Map<VehicleID, Vehicle> vehicles;
    private final Map<String, CustomerRecord> customers;
    private final Map<String, Set<Vehicle>> vehiclesOnRent;

    public VehicleManager() {
        vehicles = new HashMap<>();
        customers = new HashMap<>();
        vehiclesOnRent = new HashMap<>();
    }

    public Vehicle addVehicle(String vehicleType) {
        Vehicle newVehicle;
        VehicleID id = VehicleID.getInstance(vehicleType);

        if (vehicleType.equalsIgnoreCase("car")) {
            newVehicle = new Car(id);
        } else if (vehicleType.equalsIgnoreCase("van")) {
            newVehicle = new Van(id);
        } else {
            throw new IllegalArgumentException("Invalid vehicle type: " + vehicleType);
        }

        vehicles.put(id, newVehicle);
        return newVehicle;
    }

    public int noOfAvailableVehicles(String vehicleType) {
        int count = 0;
        for (Vehicle v : vehicles.values()) {
            if (v.getVehicleType().equalsIgnoreCase(vehicleType) && !v.isHired()) {
                count++;
            }
        }
        return count;
    }

    public CustomerRecord addCustomerRecord(String firstName, String lastName, Date dob, Boolean hasCommercialLicense) {
        Name newName = new Name(firstName, lastName);

        for (CustomerRecord cr : customers.values()) {
            if (cr.getName().equals(newName) && cr.getDateOfBirth().equals(dob)) {
                throw new IllegalArgumentException("This customer record already exists");
            }
        }

        String idString = "Customer" + (customers.size() + 1);
        CustomerRecord newRecord = new CustomerRecord(newName, dob, hasCommercialLicense, idString);

        customers.put(newRecord.getCustomerID(), newRecord);
        return newRecord;
    }

    public boolean hireVehicle(CustomerRecord customerRecord, String vehicleType, int duration) {
        String custID = customerRecord.getCustomerID();

        Set<Vehicle> rentedSet = vehiclesOnRent.get(custID);
        if (rentedSet != null && rentedSet.size() >= 3) {
            System.out.println("Customer has already hired 3 vehicles.");
            return false;
        }

        Calendar currentCal = Calendar.getInstance();
        int currentYear = currentCal.get(Calendar.YEAR);
        Calendar birthCal = Calendar.getInstance();
        birthCal.setTime(customerRecord.getDateOfBirth());
        int age = currentYear - birthCal.get(Calendar.YEAR);

        if (vehicleType.equalsIgnoreCase("car")) {
            if (age < 18) {
                System.out.println("Underage for car hire.");
                return false;
            }
        } else if (vehicleType.equalsIgnoreCase("van")) {
            if (age < 23 || !customerRecord.hasCommercialLicense()) {
                System.out.println("Ineligible for van hire.");
                return false;
            }
        }

        Vehicle selectedVehicle = null;
        for (Vehicle v : vehicles.values()) {
            if (v.getVehicleType().equalsIgnoreCase(vehicleType) && !v.isHired()) {
                if (v instanceof Van && ((Van) v).getInspection()) continue;
                if (v.getCurrentMileage() >= v.getDistanceRequirement()) continue;

                selectedVehicle = v;
                break;
            }
        }

        if (selectedVehicle == null) {
            System.out.println("No available vehicle: " + vehicleType);
            return false;
        }

        if (rentedSet == null) {
            rentedSet = new HashSet<>();
            vehiclesOnRent.put(custID, rentedSet);
        }

        rentedSet.add(selectedVehicle);
        selectedVehicle.setHired(true);

        if (selectedVehicle instanceof Van && duration >= 10) {
            ((Van) selectedVehicle).setInspection(true);
        }

        return true;
    }

    public void returnVehicle(VehicleID vehicleID, CustomerRecord customerRecord, int mileage) {
        Vehicle v = vehicles.get(vehicleID);
        if (v == null || !v.isHired()) return;

        v.setHired(false);
        v.setCurrentMileage(v.getCurrentMileage() + mileage);
        v.performServiceIfDue();

        String custID = customerRecord.getCustomerID();
        Set<Vehicle> rentedSet = vehiclesOnRent.get(custID);
        if (rentedSet != null) {
            rentedSet.remove(v);
            if (rentedSet.isEmpty()) {
                vehiclesOnRent.remove(custID);
            }
        }
    }

    public Collection<Vehicle> getVechilesByCustomer(CustomerRecord customerRecord) {
        String custID = customerRecord.getCustomerID();
        Set<Vehicle> list = vehiclesOnRent.get(custID);
        if (list == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableCollection(list);
    }
}