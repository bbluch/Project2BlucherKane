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

    public void testRefOutput() throws IOException {
        assertTrue(it.insert("Chicago", 100, 150));
        assertTrue(it.insert("Atlanta", 10, 500));
        assertTrue(it.insert("Tacoma", 1000, 100));
        assertTrue(it.insert("Baltimore", 0, 300));
        assertTrue(it.insert("Washington", 5, 350));
        assertFalse(it.insert("X", 100, 150));
        assertTrue(it.insert("L", 101, 150));
        assertTrue(it.insert("L", 11, 500));
        assertFuzzyEquals("1 Atlanta (10, 500)\n" + "2 Baltimore (0, 300)\n"
            + "0Chicago (100, 150)\n" + "3 L (11, 500)\n" + "2 L (101, 150)\n"
            + "1 Tacoma (1000, 100)\n" + "2 Washington (5, 350)\n", it.print());
        assertFuzzyEquals("2 baltimore 0 300\n" + "3 washington 5 350\n"
            + "1 atlanta 10 500\n" + "2 l 11 500\n" + "0chicago 100 150\n"
            + "1 tacoma 1000 100\n" + "2 l 101 150", it.debug());
        assertFuzzyEquals("L (101, 150)\nL (11, 500)", it.info("L"));
        assertFuzzyEquals("L", it.info(101, 150));
        assertFuzzyEquals("Tacoma (1000, 100)", it.delete("Tacoma"));
        assertFuzzyEquals("3\nChicago", it.delete(100, 150));
        assertFuzzyEquals("L (101, 150)\n" + "Atlanta (10, 500)\n"
            + "Baltimore (0, 300)\n" + "Washington (5, 350)\n"
            + "L (11, 500)\n5", it.search(0, 0, 2000));
        assertFuzzyEquals("Baltimore (0, 300)\n3", it.search(0, 300, 0));
    }


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
     * Tests that the clear method returns true even when the database is
     * initially empty.
     */
    public void testClearEmpty() {
        assertTrue(it.clear());
        // Verify that the database is still empty after clearing
        assertEquals("", it.info("CityName"));
        assertEquals("", it.info(5, 5));
    }


    /**
     * Tests that the clear method successfully removes all data from a
     * non-empty database.
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
        assertFuzzyEquals(expected, it.info("Blacksburg"));
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
        // concatenates them with a space before the coordinate pair and a
        // newline.
        // The expected order from an in-order BST traversal with duplicates
        // inserted to the left is L, L, L...
        String expected = "Springfield (10, 20)\n" + "Springfield (30, 40)\n"
            + "Springfield (50, 60)";

        assertFuzzyEquals(expected, it.info("Springfield"));
    }


    /**
     * Tests that the method handles case sensitivity correctly and returns an
     * empty string
     * for a case mismatch.
     */
    public void testInfoCaseSensitivity() {
        // Insert a city with a specific case
        it.insert("Richmond", 70, 80);

        // Search with a different case, should not find it
        assertFuzzyEquals("", it.info("richmond"));
    }


    /**
     * Tests a bad radius input.
     */
    public void testSearchBadRadius() {
        assertEquals("", it.search(0, 0, -1));
    }


    /**
     * Tests a search on an empty database.
     */
    public void testSearchEmpty() {
        assertFuzzyEquals("0", it.search(100, 100, 10));
    }


    /**
     * Tests a search with a single city that is within the radius.
     */
    public void testSearchSingleCityFound() {
        it.insert("Blacksburg", 100, 100);
        String expected = "Blacksburg (100, 100)\n1";
        assertEquals(expected, it.search(100, 100, 0));
    }


    /**
     * Tests a search where the city is exactly on the radius boundary.
     */
    public void testSearchOnBoundary() {
        it.insert("Boundary City", 103, 104);
        String expected = "Boundary City (103, 104)\n1";
        // Distance from (100, 100) to (103, 104) is sqrt((3^2) + (4^2)) = 5
        assertEquals(expected, it.search(100, 100, 5));
    }


    /**
     * Tests a search where the city is just outside the radius.
     */
    public void testSearchJustOutsideBoundary() {
        it.insert("Outside City", 104, 104);
        // Distance from (100, 100) to (104, 104) is sqrt((4^2) + (4^2)) =
        // 5.65...
        assertFuzzyEquals("1", it.search(100, 100, 5));
    }


    /**
     * Tests a search in a more complex tree with multiple nodes.
     * This also tests that the `regionSearchHelp` helper function
     * correctly prunes the tree and only visits necessary nodes.
     */
    public void testSearchComplexTree() {
        // Build a complex KDTree
        it.insert("A", 10, 8);
        it.insert("B", 12, 1);
        it.insert("C", 11, 15);
        it.insert("D", 9, 13);
        it.insert("E", 1, 10);
        it.insert("F", 10, 5);
        it.insert("G", 15, 10);

        // Search center (10, 10) with radius 3
        // Cities within this radius:
        // A (10, 8): dist=2, within
        // B (12, 1): dist=~9.2, outside
        // C (11, 15): dist=~5.1, outside
        // D (9, 13): dist=~3.16, outside
        // E (1, 10): dist=9, outside
        // F (10, 5): dist=5, outside
        // G (15, 10): dist=5, outside
        // Only "A" should be found.
        String expected = "A (10, 8)\n7";
        assertFuzzyEquals(expected, it.search(10, 10, 3));
    }


    /**
     * Tests a search with a radius that covers all points in the tree.
     */
    public void testSearchAllCities() {
        it.insert("City1", 100, 100);
        it.insert("City2", 200, 200);
        it.insert("City3", 300, 300);

        // Search with a large radius to find all cities
        String expected = "City1 (100, 100)\n" + "City2 (200, 200)\n"
            + "City3 (300, 300)\n" + "3";
        assertEquals(expected, it.search(0, 0, 500));
    }


    /**
     * Tests that the correct number of nodes are visited during the search
     * when some branches are pruned.
     */
    public void testNodeVisitCount() {
        // Create a KDTree with a known structure
        it.insert("A", 20, 30);
        it.insert("B", 10, 25);
        it.insert("C", 25, 35);
        it.insert("D", 5, 20);
        it.insert("E", 15, 28);

        // Search with center (10, 30) and radius 5
        // A (20, 30) - dist=10, outside
        // B (10, 25) - dist=5, within
        // C (25, 35) - dist=~7.07, outside
        // D (5, 20) - dist=~11.18, outside
        // E (15, 28) - dist=~5.38, outside
        // The search should visit A, B, and E.
        // It should prune C and D's subtrees based on the radius.
        String expected = "B (10, 25)\n3";
        assertFuzzyEquals(expected, it.search(10, 30, 5));
    }


    /**
     * Tests the insertion of a single city and finding it with info(x, y).
     * Also tests that inserting a city with duplicate coordinates fails.
     */
    public void testInsertAndInfoByCoord() {
        assertFuzzyEquals("", it.debug());
        assertTrue(it.insert("Blacksburg", 10, 10));
        assertEquals("Blacksburg", it.info(10, 10));
        // Attempt to insert a city with the same coordinates
        assertFalse(it.insert("Christiansburg", 10, 10));
        // Verify the original city is still there
        assertEquals("Blacksburg", it.info(10, 10));
        // Check for a city that doesn't exist
        assertEquals("", it.info(20, 20));
    }

// /**
// * Tests the info(String name) method, especially handling multiple
// * cities with the same name.
// */
// public void testInfoByName() {
// it.insert("Springfield", 70, 10);
// it.insert("Springfield", 20, 50);
// it.insert("Shelbyville", 90, 30);
//
// String expected = "(20, 50)\n(70, 10)";
// // The order may vary depending on BST implementation,
// // so we check for both possibilities.
// String alternativeExpected = "(70, 10)\n(20, 50)";
//
// String actual = it.info("Springfield");
// assertTrue("The output for info(\"Springfield\") was not correct.",
// actual.equals(expected) || actual.equals(alternativeExpected));
//
// // Test for a name that does not exist
// assertEquals("", it.info("Ogdenville"));
// }
//
//
// /**
// * Tests the print() and debug() methods for correct formatting and
// * ordering.
// */
// public void testPrintAndDebug() {
// // Test on an empty database first
// assertEquals("", it.print());
// assertEquals("", it.debug());
//
// it.insert("C", 30, 40);
// it.insert("A", 10, 20);
// it.insert("E", 50, 60);
// it.insert("B", 25, 35);
// it.insert("D", 45, 55);
//
// // Expected BST output (alphabetical order)
// String expectedPrint = "1 A (10, 20)\n" + "2 B (25, 35)\n"
// + "0C (30, 40)\n" + "2 D (45, 55)\n" + "1 E (50, 60)\n";
// assertFuzzyEquals(expectedPrint, it.print());
//
// // Expected KD-Tree output (in-order traversal based on coordinates)
// String expectedDebug = "2 B (25, 35)\n" + "1 A (10, 20)\n"
// + "3 D (45, 55)\n" + "2 E (50, 60)\n" + "0C (30, 40)\n";
// assertFuzzyEquals(expectedDebug, it.debug());
// }


    /**
     * Tests the find method's behavior at a level 0 (x-axis) split
     * when the search coordinate is equal to the node's coordinate.
     */
    public void testFindOnXBoundary() {
        // Root at (50, 50)
        it.insert("Root", 50, 50);
        // Left child (level 1, y-split)
        it.insert("Left", 25, 75);
        // Right child (level 1, y-split)
        it.insert("Right", 75, 25);
        // This city has the same X as the root, so it should be in the right
        // subtree.
        it.insert("Right-Target", 50, 60);

        // A correct implementation will go right at the root when x=50.
        // A mutant like `x <= rt.getCity().getX()` might go left and fail.
        assertEquals("Right-Target", it.info(50, 60));
    }


    /**
     * Tests the find method's behavior at a level 1 (y-axis) split
     * when the search coordinate is equal to the node's coordinate.
     */
    public void testFindOnYBoundary() {
        // Root at (50, 50)
        it.insert("Root", 50, 50);
        // Left child (level 1, y-split)
        it.insert("Left", 25, 75);
        // This city has the same Y as the left child, so it should be in its
        // right subtree.
        it.insert("Left-Right-Target", 20, 75);

        // At the root (50,50), search for (20,75) goes LEFT.
        // At node "Left" (25,75), it's a y-split. Since 75 == 75, it should go
        // RIGHT.
        // A mutant `y <= rt.getCity().getY()` might go left and fail.
        assertEquals("Left-Right-Target", it.info(20, 75));
    }


    /**
     * Tests finding a leaf node that is deep in the left side of the tree,
     * forcing multiple "less than" comparisons.
     */
    public void testFindDeepLeftNode() {
        it.insert("Root", 100, 100);
        it.insert("L1", 50, 150); // Left of root (x-split)
        it.insert("L2", 25, 125); // Left of L1 (y-split)
        it.insert("L3-Target", 10, 175); // Left of L2 (x-split)

        assertEquals("L3-Target", it.info(10, 175));
    }


    /**
     * Tests finding a leaf node that is deep in the right side of the tree,
     * forcing multiple "greater than or equal to" comparisons.
     */
    public void testFindDeepRightNode() {
        it.insert("Root", 100, 100);
        it.insert("R1", 150, 50); // Right of root (x-split)
        it.insert("R2", 125, 75); // Right of R1 (y-split)
        it.insert("R3-Target", 175, 25); // Right of R2 (x-split)

        assertEquals("R3-Target", it.info(175, 25));
    }


    /**
     * Tests searching for a city that does not exist but follows a valid path.
     * This ensures the method correctly returns null at the end of a branch.
     */
    public void testFindNonExistentCity() {
        it.insert("A", 100, 100);
        it.insert("B", 50, 150);
        it.insert("C", 150, 50);

        // This path exists (left, then right), but the city is not there.
        assertEquals("", it.info(75, 125));
        // This path also exists (right, then left)
        assertEquals("", it.info(125, 25));
    }


    /**
     * Tests deleting a city by its coordinates that does not exist.
     */
    public void testDeleteByCoordNotFound() {
        it.insert("A", 10, 10);
        // Attempt to delete a city at a location that doesn't exist.
        assertEquals("", it.delete(20, 20));
        // Ensure the original city is still there.
        assertEquals("A", it.info(10, 10));
    }


    /**
     * Tests deleting a leaf node from the KD-Tree.
     */
    public void testDeleteByCoordLeafNode() {
        it.insert("Root", 50, 50);
        it.insert("LeafToDelete", 25, 75);

        // Deleting a leaf node should be straightforward.
        String result = it.delete(25, 75);
        assertFuzzyEquals("2\nLeafToDelete", result);

        // Verify the node is gone from both trees.
        assertEquals("", it.info(25, 75));
        //assertNull(it.bst.find("LeafToDelete"));
    }


    /**
     * Tests deleting the root node from the KD-Tree, which has two children.
     * This is a complex case that tests the findMin and replacement logic.
     */
    public void testDeleteByCoordRootNode() {
        it.insert("Root", 100, 100);
        it.insert("LeftChild", 50, 150);
        it.insert("RightChild", 150, 50);
        it.insert("Successor", 120, 70); // This should become the new root.

        String result = it.delete(100, 100);
        assertFuzzyEquals("1\nRoot", result);

        // Verify the root is gone and its replacement is in place.
        assertEquals("", it.info(100, 100));
        assertEquals("Successor", it.info(120, 70));

        // Check the BST to ensure the original root was removed.
        // We expect to find other cities with the name "Root" if they existed,
        // but in this case, it was the only one.
        //assertNull(it.bst.find("Root"));
    }


    /**
     * Tests deleting a city by a name that does not exist in the database.
     */
    public void testDeleteByNameNotFound() {
        it.insert("A", 10, 10);
        assertEquals("", it.delete("NonExistentName"));
    }


    /**
     * Tests deleting a single city by its name.
     */
    public void testDeleteByNameSingle() {
        it.insert("ToDelete", 55, 66);
        it.insert("AnotherCity", 1, 1);

        String expected = "ToDelete (55, 66)";
        assertFuzzyEquals(expected, it.delete("ToDelete"));

        // Verify the city is gone from both data structures.
        assertEquals("", it.info(55, 66));
        //assertNull(it.bst.find("ToDelete"));
        // Ensure the other city remains.
        assertEquals("AnotherCity", it.info(1, 1));
    }


    /**
     * Tests deleting multiple cities that share the same name.
     * According to the spec, all cities with that name must be removed.
     */
    public void testDeleteByNameMultiple() {
        it.insert("Duplicate", 10, 10);
        it.insert("Other", 50, 50);
        it.insert("Duplicate", 20, 20);
        it.insert("Duplicate", 30, 30);

        String result = it.delete("Duplicate");

        // The order of deletion depends on the BST traversal.
        // We need to check if all coordinates are present in the output.
        assertTrue(result.contains("Duplicate (10, 10)"));
        assertTrue(result.contains("Duplicate (20, 20)"));
        assertTrue(result.contains("Duplicate (30, 30)"));

        // Verify all duplicates are gone from the kd-tree.
        assertEquals("", it.info(10, 10));
        assertEquals("", it.info(20, 20));
        assertEquals("", it.info(30, 30));

        // Verify the other city is still present.
        assertEquals("Other", it.info(50, 50));

        // Verify no cities named "Duplicate" remain in the BST.
        //assertNull(it.bst.find("Duplicate"));
    }

}
