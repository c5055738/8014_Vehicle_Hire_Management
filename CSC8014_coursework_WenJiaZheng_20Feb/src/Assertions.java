 
/** 
 * Assertions - a set of static utility methods to assert the value 
 * of objects and conditions etc. for testing purposes. All methods throw an 
 * AssertionError if the given values or conditions are not as asserted.
 * 
 * See JUnit <http://www.junit.org/> for a more systematic framework for
 * unit testing that is integrated with most IDEs.
 * 
 * @author Nick Cook &lt;nick.cook@ncl.ac.uk&gt;
 * @version svn: $Revision: 4037 $<br>
 * $Date: 2015-10-28 14:58:39 +0000 (Wed, 28 Oct 2015) $<br>
 * Copyright (C) 2011 Newcastle University, UK
 */

public class Assertions {
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Assertions() { }

    /**
     * Assert that the given boolean value is true.
     *
     * @param b the value to test
     * @throws AssertionError if <code>b</code> is <code>false</code>
     */
    public static void assertTrue(boolean b) {
        // Throw AssertionError when value is not true
        if (!b)
            throw new AssertionError(buildMessage(true, b));
    }
    
    /**
     * Assert that the given boolean value is false.
     *
     * @param b the value to test
     * @throws AssertionError if <code>b</code> is <code>true</code>
     */
    public static void assertFalse(boolean b) {
        // Throw AssertionError when value is not false
        if (b)
            throw new AssertionError(buildMessage(false, b));
    }
    
    /**
     * Assert that a given int is equal to the expected value for the
     * int.
     *
     * @param expected the expected value of an int for equality testing
     * @param actual the actual value of the int to compare with the 
     * expected value
     * @throws AssertionError if <code>expected</code> is not equal to
     * <code>actual</code>, i.e. throws AssertionError if
     * <code>expected == actual</code> is <code>false</code>
     */
    public static void assertEquals(int expected, int actual) {
        // Throw AssertionError when integers are not equal
        if (expected != actual)
            throw new AssertionError(buildMessage(expected, actual));
    }

    /**
     * Assert that a given object is equal to the expected value for the
     * object.
     *
     * @param expected the expected value of an object for equality testing
     * @param actual the actual value of the object to compare with the 
     * expected value
     * @throws AssertionError if <code>expected</code> is not equal to
     * <code>actual</code>, i.e. throws AssertionError if
     * <code>expected.equals(actual)</code> is <code>false</code>
     */
    public static void assertEquals(Object expected, Object actual) {
        // Throw AssertionError when objects are not equal
        if (!expected.equals(actual))
            throw new AssertionError(buildMessage(expected, actual));
    }

    /**
     * Assert that the two objects are not equal.
     *
     * @param obj1 an object to test for not being equal to another
     * @param obj2 an object to test for not being equal to another
     * @throws AssertionError if <code>obj1</code> is  equal to
     * <code>obj2</code>, i.e. throws AssertionError if
     * <code>obj1.equals(obj2)</code> is <code>true</code>
     */
    public static void assertNotEquals(Object obj1, Object obj2) {
        // Throw AssertionError when objects should not be equal
        if (obj1.equals(obj2))
            throw new AssertionError(
                "\"" + obj1 + "\" and \"" + obj2 + "\" should not be equal");
    }

    /**
     * Assert that an object is <code>null</code>.
     *
     * @param o the object to test for nullity
     * @throws AssertionError if <code>o</code> is not <code>null</code>
     */
    public static void assertNull(Object o) {
        // Throw AssertionError when object is not null
        if (o != null)
            throw new AssertionError(buildMessage(null, o));
    }
    
    /**
     * Assert that an object is not <code>null</code>.
     *
     * @param o the object to test for non-nullity
     * @throws AssertionError if <code>o</code> is <code>null</code>
     */
    public static void assertNotNull(Object o) {
        // Throw AssertionError when object is null
        if (o == null)
            throw new AssertionError(buildMessage("not null", o));
    }
    
    /**
     * Assert that a given <code>Throwable</code> is of the same type as the 
     * given expected <code>Throwable</code> class.
     *
     * @param expectedClass the expected class of the <code>Throwable</code>
     * @param t the <code>Throwable</code> to compare type against 
     * <code>expectedClass</code>
     * @throws AssertionError if <code>t</code> is not an instance of
     * <code>expectedClass</code>
     */
    public static void assertExpectedThrowable(
        Class<? extends Throwable> expectedClass, Throwable t) {
        // Throw AssertionError when Throwable type does not match
        if (!expectedClass.isInstance(t)) {
            AssertionError e = new AssertionError(
                buildMessage(expectedClass.getName(), t));
            
            // Attach the original throwable as cause of the assertion error
            e.initCause(t);
            
            throw e;
        }
    }
    
    /**
     * Asserts that a line of code should not be reached.
     *
     * @throws AssertionError if reach line in code that should not be 
     * executed
     */
    public static void assertNotReached() {
        throw new AssertionError("Reached code that should not be reached");
    }

    /**
     * Builds a formatted assertion failure message.
     *
     * @param expected the expected value
     * @param actual the actual value received
     * @return a string describing the mismatch
     */
    private static String buildMessage(Object expected, 
        Object actual) {
        // Build human-readable error message for assertion failures
        final StringBuilder sb = new StringBuilder("expected ");
        sb.append("\"").append(expected).append("\"");
        sb.append(", actual value is ");
        sb.append("\"").append(actual).append("\"");
        
        return sb.toString();
    }
}
