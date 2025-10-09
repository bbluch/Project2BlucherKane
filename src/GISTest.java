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
    /**
     * Tests that the clear method returns true even when the database is initially empty.
     */
    public void testClearEmpty() {
        assertTrue(it.clear());
        // Verify that the database is still empty after clearing
        assertEquals("", it.info("CityName"));
        assertEquals("", it.info(5, 5));
    }

    /**
     * Tests that the clear method successfully removes all data from a non-empty database.
     */
    public void testClearNonEmpty() {
        // Insert some data
        assertTrue(it.insert("New York", 100, 200));
        assertTrue(it.insert("Los Angeles", 300, 400));

        // Verify data is present
        assertEquals("New York", it.info(100, 200));
        assertEquals("Los Angeles", it.info(300, 400));

        // Clear the database
        assertTrue(it.clear());

        // Verify data is gone
        assertEquals("", it.info(100, 200));
        assertEquals("", it.info(300, 400));
        assertEquals("", it.info("New York"));
        assertEquals("", it.info("Los Angeles"));
    }

    /**
     * Tests that the database remains functional after being cleared.
     */
    public void testClearAndReinsert() {
        // Insert initial data
        assertTrue(it.insert("Initial City", 50, 50));
        assertTrue(it.info(50, 50).equals("Initial City"));

        // Clear the database
        assertTrue(it.clear());

        // Insert new data
        assertTrue(it.insert("Second City", 60, 60));

        // Verify the new data is present and the old data is gone
        assertEquals("Second City", it.info(60, 60));
        assertEquals("", it.info(50, 50));
    }
    
    /**
     * Tests that an empty string is returned when a city with the specified
     * name is not in the database.
     */
    public void testInfoNotFound() {
        // Insert a city that will not be found.
        it.insert("Los Angeles", 100, 200);
        
        // Search for a city that doesn't exist.
        assertEquals("", it.info("Nonexistent City"));
    }

    /**
     * Tests that the correct information is returned for a single city found
     * by name.
     */
    public void testInfoSingleCityFound() {
        // Insert a single city
        it.insert("Blacksburg", 50, 60);

        // Verify the info method returns the correct string
        String expected = "Blacksburg (50, 60)";
        assertEquals(expected, it.info("Blacksburg"));
    }

    /**
     * Tests that all cities with a matching name are returned, each on a new
     * line, and the string is properly formatted.
     */
    public void testInfoMultipleCitiesFound() {
        // Insert multiple cities with the same name
        it.insert("Springfield", 10, 20);
        it.insert("Springfield", 30, 40);
        it.insert("Springfield", 50, 60);

        // The order of insertion into the BST (and therefore traversal)
        // is based on the problem specification, which states equal values
        // insert to the left. The `findAll` method is expected to return them
        // in an order, likely based on the tree traversal. The `info` method
        // concatenates them with a space before the coordinate pair and a newline.
        // The expected order from an in-order BST traversal with duplicates
        // inserted to the left is L, L, L...
        String expected = "Springfield (10, 20)\n"
            + "Springfield (30, 40)\n"
            + "Springfield (50, 60)";
        
        assertEquals(expected, it.info("Springfield"));
    }
    
    /**
     * Tests that the method handles case sensitivity correctly and returns an empty string
     * for a case mismatch.
     */
    public void testInfoCaseSensitivity() {
        // Insert a city with a specific case
        it.insert("Richmond", 70, 80);

        // Search with a different case, should not find it
        assertEquals("", it.info("richmond"));
    }

    /**
     * Tests that the returned string is trimmed correctly, with no extra
     * whitespace at the beginning or end.
     */
    public void testInfoStringTrim() {
        // Insert a city
        it.insert("Test City", 1, 1);
        
        // Check that the returned string is trimmed.
        // The method uses trim() at the end, so no extra spaces should be present.
        String result = it.info("Test City");
        assertEquals("Test City (1, 1)", result);
        assertFalse(result.startsWith(" "));
        assertFalse(result.endsWith(" "));
        assertFalse(result.endsWith("\n"));
    }
    
    
    
    
    
}
