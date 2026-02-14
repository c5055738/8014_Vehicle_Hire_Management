import java.util.*;

/**
 * Main class for testing the Vehicle Hire Management System.
 * This class performs comprehensive testing including normal operations,
 * boundary conditions, and exceptional cases as required by the specification.
 */
public class Main {

    /**
     * Executes all test suites and verifies the system logic.
     */
    public static void main(String[] args) {
        try {
            System.out.println("Starting full system boundary testing...\n");

            // Task 5: Core functionality testing
            testVehicleManager();

            // Task 5: Boundary condition testing
            testAgeBoundaries();
            testHiringLimit();
            testServiceMileageBoundary();

            // Task 5: ID generation and uniqueness testing
            testVehicleIDRandomness();

            System.out.println("\nCongratulations! All assertions and boundary tests passed!");
        } catch (Throwable t) {
            System.out.println("\nTest failed! Logic error detected:");
            t.printStackTrace();
        }
    }

    /**
     * Tests basic operations: adding records, hiring, and returning vehicles.
     * Also tests exceptional cases like duplicate records and underage hiring.
     */
    public static void testVehicleManager() {
        VehicleManager vehicleManager = new VehicleManager();
        vehicleManager.addVehicle("car");
        vehicleManager.addVehicle("van");

        // Verify initial vehicle counts
        Assertions.assertEquals(1, vehicleManager.noOfAvailableVehicles("car"));
        Assertions.assertEquals(1, vehicleManager.noOfAvailableVehicles("van"));

        // Setup a standard adult customer (Christy, 1991)
        Calendar calendar = Calendar.getInstance();
        calendar.set(1991, Calendar.JANUARY, 1);
        Date dobAdult = calendar.getTime();

        CustomerRecord adult = vehicleManager.addCustomerRecord("Christy", "Hired", dobAdult, true);
        Assertions.assertNotNull(adult);

        // Test normal hiring
        boolean carHiredSuccess = vehicleManager.hireVehicle(adult, "car", 5);
        Assertions.assertTrue(carHiredSuccess);
        Assertions.assertEquals(0, vehicleManager.noOfAvailableVehicles("car"));

        // Test exceptional case: adding duplicate customer record
        try {
            vehicleManager.addCustomerRecord("Christy", "Hired", dobAdult, true);
            Assertions.assertNotReached();
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("This customer record already exists", e.getMessage());
        }

        // Test exceptional case: underage hiring attempt
        calendar.set(2015, Calendar.JANUARY, 1);
        Date dobChild = calendar.getTime();
        CustomerRecord child = vehicleManager.addCustomerRecord("Christy", "Junior", dobChild, false);

        boolean hireUnderage = vehicleManager.hireVehicle(child, "car", 1);
        Assertions.assertFalse(hireUnderage);

        // Test vehicle return and mileage update
        Vehicle hiredCar = vehicleManager.getVechilesByCustomer(adult).iterator().next();
        VehicleID carID = hiredCar.getVehicleID();

        vehicleManager.returnVehicle(carID, adult, 100);
        Assertions.assertEquals(1, vehicleManager.noOfAvailableVehicles("car"));
        Assertions.assertEquals(100, hiredCar.getCurrentMileage());

        // Test Van-specific rule: rental >= 10 days triggers inspection
        vehicleManager.hireVehicle(adult, "van", 12);
        Vehicle hiredVan = null;
        for (Vehicle v : vehicleManager.getVechilesByCustomer(adult)) {
            if (v.getVehicleType().equalsIgnoreCase("van")) {
                hiredVan = v;
                break;
            }
        }

        if (hiredVan instanceof Van) {
            Assertions.assertTrue(((Van) hiredVan).getInspection());
        }
        System.out.println("Basic and Exception tests: PASSED");
    }

    /**
     * Verifies that the system correctly handles age boundaries (Exactly 18 and 23).
     */
    public static void testAgeBoundaries() {
        VehicleManager vm = new VehicleManager();
        vm.addVehicle("car");
        vm.addVehicle("van");

        Calendar cal = Calendar.getInstance();

        // Boundary: Exactly 18 years old (Allowed for Car)
        cal.add(Calendar.YEAR, -18);
        CustomerRecord user18 = vm.addCustomerRecord("Christy", "Eighteen", cal.getTime(), false);
        Assertions.assertTrue(vm.hireVehicle(user18, "car", 1));

        // Boundary: Exactly 23 years old with license (Allowed for Van)
        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -23);
        CustomerRecord user23 = vm.addCustomerRecord("Christy", "TwentyThree", cal.getTime(), true);
        Assertions.assertTrue(vm.hireVehicle(user23, "van", 1));

        System.out.println("Age Boundary tests (Exact 18/23): PASSED");
    }

    /**
     * Verifies that the 3-vehicle rental limit is strictly enforced.
     */
    public static void testHiringLimit() {
        VehicleManager vm = new VehicleManager();
        for(int i = 0; i < 4; i++) vm.addVehicle("car");

        Calendar cal = Calendar.getInstance();
        cal.set(1991, Calendar.JANUARY, 1);
        CustomerRecord tester = vm.addCustomerRecord("Christy", "LimitTester", cal.getTime(), false);

        // Success for first 3 rentals
        Assertions.assertTrue(vm.hireVehicle(tester, "car", 1));
        Assertions.assertTrue(vm.hireVehicle(tester, "car", 1));
        Assertions.assertTrue(vm.hireVehicle(tester, "car", 1));

        // Failure for 4th rental attempt
        Assertions.assertFalse(vm.hireVehicle(tester, "car", 1));

        System.out.println("Hiring Limit tests (Max 3 vehicles): PASSED");
    }

    /**
     * Verifies that vehicle mileage is reset after service threshold is reached.
     */
    public static void testServiceMileageBoundary() {
        VehicleManager vm = new VehicleManager();
        vm.addVehicle("car");

        Calendar cal = Calendar.getInstance();
        cal.set(1991, Calendar.JANUARY, 1);
        CustomerRecord tester = vm.addCustomerRecord("Service", "Tester", cal.getTime(), false);

        vm.hireVehicle(tester, "car", 1);
        Vehicle car = vm.getVechilesByCustomer(tester).iterator().next();
        VehicleID id = car.getVehicleID();

        // Boundary: Reaching exactly 10,000 miles for car service
        vm.returnVehicle(id, tester, 10000);

        // Ensure mileage is reset to 0
        Assertions.assertEquals(0, car.getCurrentMileage());

        System.out.println("Service Mileage Boundary test (10000 miles reset): PASSED");
    }

    /**
     * Verifies ID generation rules: prefix, even/odd numbering, and uniqueness.
     */
    public static void testVehicleIDRandomness() {
        VehicleManager vm = new VehicleManager();
        Set<String> idSet = new HashSet<>();
        int testCount = 10;

        System.out.println("--- Random ID Generation Test (Generating " + (testCount * 2) + " vehicles) ---");

        // 1. Test Car IDs: Must start with 'C' and end with an EVEN number
        for (int i = 0; i < testCount; i++) {
            Vehicle car = vm.addVehicle("car");
            String id = car.getVehicleID().toString();
            idSet.add(id);

            // Validate Car prefix
            Assertions.assertTrue(id.startsWith("C"));

            // Validate Car even number using robust split logic
            String[] parts = id.split("-");
            int lastNum = Integer.parseInt(parts[1]);
            Assertions.assertTrue(lastNum % 2 == 0);

            System.out.println("Generated Car ID: " + id);
        }

        // 2. Test Van IDs: Must start with 'V' and end with an ODD number
        for (int i = 0; i < testCount; i++) {
            Vehicle van = vm.addVehicle("van");
            String id = van.getVehicleID().toString();
            idSet.add(id);

            // Validate Van prefix
            Assertions.assertTrue(id.startsWith("V"));

            // Validate Van odd number using robust split logic
            String[] parts = id.split("-");
            int lastNum = Integer.parseInt(parts[1]);
            Assertions.assertTrue(lastNum % 2 != 0);

            System.out.println("Generated Van ID: " + id);
        }

        // 3. Final Uniqueness check: All IDs in the Set must be unique
        Assertions.assertEquals(testCount * 2, idSet.size());
        System.out.println("Random ID Uniqueness and Rule Validation: PASSED\n");
    }
}