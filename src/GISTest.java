import java.io.IOException;
import student.TestCase;

/**
 * @author benblucher, austink23
 * @version 10.2.25
 */
public class GISTest extends TestCase {

    private GIS it;

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        it = new GISDB();
    }


    /**
     * Test clearing on initial
     * 
     * @throws IOException
     */
    public void testRefClearInit() throws IOException {
        assertTrue(it.clear());
    }


    /**
     * Print testing for empty trees
     * 
     * @throws IOException
     */
    public void testRefEmptyPrints() throws IOException {
        assertFuzzyEquals("", it.print());
        assertFuzzyEquals("", it.debug());
        assertFuzzyEquals("", it.info("CityName"));
        assertFuzzyEquals("", it.info(5, 5));
        assertFuzzyEquals("", it.delete("CityName"));
        assertFuzzyEquals("", it.delete(5, 5));
    }


    /**
     * Print bad input checks
     * 
     * @throws IOException
     */
    public void testRefBadInput() throws IOException {
        assertFalse(it.insert("CityName", -1, 5));
        assertFalse(it.insert("CityName", 5, -1));
        assertFalse(it.insert("CityName", 100000, 5));
        assertFalse(it.insert("CityName", 5, 100000));
        assertFuzzyEquals("", it.search(-1, -1, -1));
    }


    // ----------------------------------------------------------
    /**
     * Tests an insert returns true with correct values.
     */
    public void testInsertValidCoordinates() {
        assertTrue(it.insert("Blacksburg", 100, 100));
    }


    // ----------------------------------------------------------
    /**
     * Tests the case where coordinates are 0.
     */
    public void testInsertAtOrigin() {
        assertTrue(it.insert("Origin City", 0, 0));
    }


    // ----------------------------------------------------------
    /**
     * Tests with max coordinates.
     */
    public void testInsertAtMaxCoordinates() {
        assertTrue(it.insert("Max City", GISDB.MAXCOORD, GISDB.MAXCOORD));
    }


    // ----------------------------------------------------------
    /**
     * Tests with negative X coordinate.
     */
    public void testInsertWithNegativeX() {
        assertFalse(it.insert("West of Here", -1, 100));
    }


    // ----------------------------------------------------------
    /**
     * Tests with negative Y coordinate.
     */
    public void testInsertWithNegativeY() {
        assertFalse(it.insert("South of Here", 100, -1));
    }


    // ----------------------------------------------------------
    /**
     * Tests insert with too large of X value.
     */
    public void testInsertWithExcessiveX() {
        assertFalse(it.insert("East of Here", 100000, 100));
    }


    // ----------------------------------------------------------
    /**
     * Tests insert with too large of Y value.
     */
    public void testInsertWithExcessiveY() {
        assertFalse(it.insert("North of Here", 100, 100000));
    }


    // ----------------------------------------------------------
    /**
     * Tests insert with both coordinates being invalid.
     */
    public void testInsertWithBothInvalidCoordinates() {
        assertFalse(it.insert("Nowhere", -10, 100000));
    }
    
    /**
     * Insert some records and check output requirements for various commands
     * 
     * @throws IOException
     */
    /*
     * public void testRefOutput() throws IOException {
     * assertTrue(it.insert("Chicago", 100, 150));
     * assertTrue(it.insert("Atlanta", 10, 500));
     * assertTrue(it.insert("Tacoma", 1000, 100));
     * assertTrue(it.insert("Baltimore", 0, 300));
     * assertTrue(it.insert("Washington", 5, 350));
     * assertFalse(it.insert("X", 100, 150));
     * assertTrue(it.insert("L", 101, 150));
     * assertTrue(it.insert("L", 11, 500));
     * assertFuzzyEquals("1  Atlanta (10, 500)\n" + "2    Baltimore (0, 300)\n"
     * + "0Chicago (100, 150)\n" + "3      L (11, 500)\n"
     * + "2    L (101, 150)\n" + "1  Tacoma (1000, 100)\n"
     * + "2    Washington (5, 350)\n", it.print());
     * assertFuzzyEquals("2    Baltimore (0, 300)\n"
     * + "3      Washington (5, 350)\n" + "4        L (11, 500)\n"
     * + "1  Atlanta (10, 500)\n" + "0Chicago (100, 150)\n"
     * + "1  Tacoma (1000, 100)\n" + "2    L (101, 150)\n", it.debug());
     * assertFuzzyEquals("L (101, 150)\nL (11, 500)", it.info("L"));
     * assertFuzzyEquals("L", it.info(101, 150));
     * assertFuzzyEquals("Tacoma (1000, 100)", it.delete("Tacoma"));
     * assertFuzzyEquals("3\nChicago", it.delete(100, 150));
     * assertFuzzyEquals("L (101, 150)\n" + "Atlanta (10, 500)\n"
     * + "Baltimore (0, 300)\n" + "Washington (5, 350)\n"
     * + "L (11, 500)\n5", it.search(0, 0, 2000));
     * assertFuzzyEquals("Baltimore (0, 300)\n3", it.search(0, 300, 0));
     * }
     */
    
    /**
     * Tests inserting a city with valid coordinates and unique location.
     */
    public void testInsertValidAndUnique() {
        assertTrue(it.insert("New York", 100, 200));
        assertTrue(it.info(100, 200).equals("New York"));
    }


    /**
     * Tests inserting a city with a name that already exists but with a new
     * location.
     */
    public void testInsertExistingNameNewLocation() {
        assertTrue(it.insert("Blacksburg", 500, 500));
        assertTrue(it.insert("Blacksburg", 501, 501));
        assertTrue(it.info(500, 500).equals("Blacksburg"));
        assertTrue(it.info(501, 501).equals("Blacksburg"));
    }


    /**
     * Tests inserting a city with a duplicate location, which should fail.
     */
    public void testInsertDuplicateLocation() {
        assertTrue(it.insert("Richmond", 300, 400));
        assertFalse(it.insert("Not Richmond", 300, 400));
    }


    /**
     * Tests inserting a city with a negative x-coordinate.
     */
    public void testInsertNegativeX() {
        assertFalse(it.insert("Invalid X", -1, 100));
    }


    /**
     * Tests inserting a city with a negative y-coordinate.
     */
    public void testInsertNegativeY() {
        assertFalse(it.insert("Invalid Y", 100, -1));
    }


    /**
     * Tests inserting a city with an x-coordinate greater than MAXCOORD.
     */
    public void testInsertXTooLarge() {
        assertFalse(it.insert("Invalid X Max", GISDB.MAXCOORD + 1, 100));
    }


    /**
     * Tests inserting a city with a y-coordinate greater than MAXCOORD.
     */
    public void testInsertYTooLarge() {
        assertFalse(it.insert("Invalid Y Max", 100, GISDB.MAXCOORD + 1));
    }
    
    
    
    
    
}
