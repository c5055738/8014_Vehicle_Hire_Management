import java.util.*;

/**
 * Manages vehicles, customers, and vehicle hire operations.
 * Handles adding vehicles and customer records, hiring and returning vehicles,
 * and enforcing business rules such as age limits and rental quotas.
 */
public class VehicleManager {
    private final Map<VehicleID, Vehicle> vehicles;
    private final Map<String, CustomerRecord> customers;
    private final Map<String, Set<Vehicle>> vehiclesOnRent;

    /**
     * Constructs a new VehicleManager with empty collections.
     */
    public VehicleManager() {
        vehicles = new HashMap<>();
        customers = new HashMap<>();
        vehiclesOnRent = new HashMap<>();
    }

    /**
     * Adds a new vehicle of the specified type to the fleet.
     *
     * @param vehicleType the type of vehicle ("car" or "van")
     * @return the newly created Vehicle
     * @throws IllegalArgumentException if vehicleType is not "car" or "van"
     */
    public Vehicle addVehicle(String vehicleType) {
        Vehicle newVehicle;
        VehicleID id = VehicleID.getInstance(vehicleType);

        // Create the appropriate vehicle subtype
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

    /**
     * Returns the count of available (not hired) vehicles of the given type.
     *
     * @param vehicleType the type of vehicle ("car" or "van")
     * @return the number of available vehicles
     */
    public int noOfAvailableVehicles(String vehicleType) {
        int count = 0;
        for (Vehicle v : vehicles.values()) {
            // Count only matching type and not currently hired
            if (v.getVehicleType().equalsIgnoreCase(vehicleType) && !v.isHired()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Adds a new customer record. Duplicate name and date of birth combination are not allowed.
     *
     * @param firstName the customer's first name
     * @param lastName the customer's last name
     * @param dob the date of birth
     * @param hasCommercialLicense whether the customer holds a commercial license
     * @return the newly created CustomerRecord
     * @throws IllegalArgumentException if a customer with same name and DOB already exists
     */
    public CustomerRecord addCustomerRecord(String firstName, String lastName, Date dob, Boolean hasCommercialLicense) {
        Name newName = new Name(firstName, lastName);

        // Check for duplicate customer (same name and DOB)
        for (CustomerRecord cr : customers.values()) {
            if (cr.getName().equals(newName) && cr.getDateOfBirth().equals(dob)) {
                throw new IllegalArgumentException("This customer record already exists");
            }
        }

        // Generate unique customer ID
        String idString = "Customer" + (customers.size() + 1);
        CustomerRecord newRecord = new CustomerRecord(newName, dob, hasCommercialLicense, idString);

        customers.put(newRecord.getCustomerID(), newRecord);
        return newRecord;
    }

    /**
     * Attempts to hire a vehicle of the specified type for the given customer and duration.
     * Enforces age limits (18+ for car, 23+ for van), commercial license for van, and max 3 vehicles per customer.
     *
     * @param customerRecord the customer requesting the hire
     * @param vehicleType the type of vehicle ("car" or "van")
     * @param duration the rental duration in days
     * @return true if hire was successful; false otherwise
     */
    public boolean hireVehicle(CustomerRecord customerRecord, String vehicleType, int duration) {
        String custID = customerRecord.getCustomerID();

        // Check rental limit: max 3 vehicles per customer
        Set<Vehicle> rentedSet = vehiclesOnRent.get(custID);
        if (rentedSet != null && rentedSet.size() >= 3) {
            System.out.println("Customer has already hired 3 vehicles.");
            return false;
        }

        // Calculate customer age
        Calendar currentCal = Calendar.getInstance();
        int currentYear = currentCal.get(Calendar.YEAR);
        Calendar birthCal = Calendar.getInstance();
        birthCal.setTime(customerRecord.getDateOfBirth());
        int age = currentYear - birthCal.get(Calendar.YEAR);

        // Enforce age and license requirements
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

        // Find an available vehicle (not hired, no inspection due, within service mileage)
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

        // Add to rented set and mark as hired
        if (rentedSet == null) {
            rentedSet = new HashSet<>();
            vehiclesOnRent.put(custID, rentedSet);
        }

        rentedSet.add(selectedVehicle);
        selectedVehicle.setHired(true);

        // Van rental for 10+ days requires inspection on return
        if (selectedVehicle instanceof Van && duration >= 10) {
            ((Van) selectedVehicle).setInspection(true);
        }

        return true;
    }

    /**
     * Processes the return of a vehicle, updating mileage and performing service if due.
     *
     * @param vehicleID the ID of the vehicle being returned
     * @param customerRecord the customer returning the vehicle
     * @param mileage the additional mileage to add (distance driven during rental)
     */
    public void returnVehicle(VehicleID vehicleID, CustomerRecord customerRecord, int mileage) {
        Vehicle v = vehicles.get(vehicleID);
        if (v == null || !v.isHired()) return;

        // Update hire status and mileage
        v.setHired(false);
        v.setCurrentMileage(v.getCurrentMileage() + mileage);
        v.performServiceIfDue();

        // Remove from customer's rented set
        String custID = customerRecord.getCustomerID();
        Set<Vehicle> rentedSet = vehiclesOnRent.get(custID);
        if (rentedSet != null) {
            rentedSet.remove(v);
            if (rentedSet.isEmpty()) {
                vehiclesOnRent.remove(custID);
            }
        }
    }

    /**
     * Returns the collection of vehicles currently hired by the given customer.
     *
     * @param customerRecord the customer whose hired vehicles to retrieve
     * @return an unmodifiable collection of vehicles; empty if none hired
     */
    public Collection<Vehicle> getVechilesByCustomer(CustomerRecord customerRecord) {
        String custID = customerRecord.getCustomerID();
        Set<Vehicle> list = vehiclesOnRent.get(custID);
        if (list == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableCollection(list);
    }

    /**
     * Returns all vehicles in the fleet.
     *
     * @return an unmodifiable collection of all vehicles
     */
    public Collection<Vehicle> getAllVehicles() {
        return Collections.unmodifiableCollection(vehicles.values());
    }

    /**
     * Returns all customer records.
     *
     * @return an unmodifiable collection of all customers
     */
    public Collection<CustomerRecord> getCustomers() {
        return Collections.unmodifiableCollection(customers.values());
    }

    /**
     * Returns the map of customer IDs to their currently hired vehicles.
     *
     * @return an unmodifiable map of customer ID to set of hired vehicles
     */
    public Map<String, Set<Vehicle>> getHiredVehicles() {
        return Collections.unmodifiableMap(vehiclesOnRent);
    }
}