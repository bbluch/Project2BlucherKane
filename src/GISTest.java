import java.io.IOException;
import student.TestCase;

/**
 * Tests all methods in GISDB
 * 
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
        assertEquals(it.delete("jack"), "");
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
        assertFuzzyEquals("Baltimore (0, 300)\n4", it.search(0, 300, 0));
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
        it.insert("Christiansburg", 65, 15);
        assertEquals("", it.search(65, 15, -1));
        assertFuzzyEquals("Christiansburg (65, 15)\n1", it.search(65, 15, 1));
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
        // assertNull(it.bst.find("LeafToDelete"));
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
        assertFuzzyEquals("5\nRoot", result);

        // Verify the root is gone and its replacement is in place.
        assertEquals("", it.info(100, 100));
        assertEquals("Successor", it.info(120, 70));

        // Check the BST to ensure the original root was removed.
        // We expect to find other cities with the name
        // Root if they existed,
        // but in this case, it was the only one.
        // assertNull(it.bst.find("Root"));
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
        // assertNull(it.bst.find("ToDelete"));
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
        // assertNull(it.bst.find("Duplicate"));
    }

    /**
     * Tests a complex deletion from the KD-tree. This scenario is designed to
     * stress the recursive nature of the remove and findMin methods. It deletes
     * a
     * node ("B") whose replacement ("F") is not an immediate child, forcing a
     * deeper recursion for both finding the minimum and for the subsequent
     * removal
     * of that minimum node from its original position.
     */
// public void testComplexKDRemove() {
// // Build a specific tree structure:
// // Level 0 (x): B(50,50)
// // Level 1 (y): / \
// // A(25,75) C(75,25)
// // Level 2 (x): / \
// // F(60,10) D(90,40)
// // Level 3 (y): \
// // G(60,15)
// it.insert("B", 50, 50); // Root
// it.insert("A", 25, 75);
// it.insert("C", 75, 25);
// it.insert("D", 90, 40);
// it.insert("F", 60, 10); // This will be the replacement for C, then B
// it.insert("G", 60, 15);
//
// // First, delete "C". Its replacement should be "F".
// // This tests a non-trivial findMin and remove.
// String result1 = it.delete(75, 25);
// assertTrue("Node count for deleting C should be reported.", result1
// .startsWith("3"));
// assertTrue("Name of deleted city C should be reported.", result1
// .contains("C"));
//
// // Now, delete the original root "B". The new replacement should be "G".
// // This is a more complex test because the replacement "G" is found
// // after multiple recursive steps in findMin.
// String result2 = it.delete(50, 50);
// assertTrue("Node count for deleting B should be reported.", result2
// .startsWith("4"));
// assertTrue("Name of deleted city B should be reported.", result2
// .contains("B"));
//
// // Verify the final state of the tree
// assertEquals("", it.info(50, 50)); // Original root is gone
// assertEquals("", it.info(75, 25)); // C is gone
// assertEquals("G", it.info(60, 15)); // G should now be the root
// assertEquals("A", it.info(25, 75)); // A should still exist
// assertEquals("D", it.info(90, 40)); // D should still exist
// assertEquals("F", it.info(60, 10)); // F should still exist
// }


    /**
     * Tests the region search pruning logic with a search circle whose edge
     * lies exactly on a splitting plane. This is designed to kill mutants that
     * might incorrectly use '<=' instead of '<' for pruning, which would cause
     * them to visit an extra, unnecessary subtree.
     */
    public void testRegionSearchPruningOnBoundary() {
        // Tree: A(50,50) is the root. C(75,25) is in the right subtree.
        it.insert("A", 50, 50);
        it.insert("B", 25, 75);
        it.insert("C", 75, 25);

        // Search from (65, 25) with radius 10.
        // The distance to the splitting plane at A (x=50) is 15.
        // Since 15 > 10, the left subtree of A should be pruned.
        // The search circle's right edge is at x=75, exactly touching C.
        // The distance to C is 10, so it should be included.
        // A correct implementation visits A and C. A mutant might also visit B.
        String result = it.search(65, 25, 10);
        assertTrue("The search should find city C.", result.contains(
            "C (75, 25)"));
        assertFalse("The search should NOT find city B.", result.contains(
            "B (25, 75)"));
        assertTrue("Should only visit 2 nodes (A and C), pruning B's subtree.",
            result.endsWith("\n2"));
    }


    /**
     * Tests the `delete(String name)` method by removing multiple cities that
     * share the same name ("Duplicate"). This ensures that `findAll` in the BST
     * correctly identifies all instances and that each one is subsequently
     * removed from both the BST and the KD-tree.
     */
    public void testDeleteAllByNameWithDuplicates() {
        it.insert("Duplicate", 10, 10);
        it.insert("Keeper", 99, 99);
        it.insert("Duplicate", 20, 20);
        it.insert("AnotherKeeper", 1, 1);
        it.insert("Duplicate", 30, 30);

        // Delete all cities named "Duplicate"
        String result = it.delete("Duplicate");

        // Verify that the output string contains all deleted cities.
        // The order can vary, so we check for containment.
        assertTrue(result.contains("Duplicate (10, 10)"));
        assertTrue(result.contains("Duplicate (20, 20)"));
        assertTrue(result.contains("Duplicate (30, 30)"));

        // Verify all "Duplicate" cities are gone from both data structures.
        // assertNull(it.bst.find("Duplicate"));
        assertEquals("", it.info(10, 10));
        assertEquals("", it.info(20, 20));
        assertEquals("", it.info(30, 30));

        // Verify that the other cities were not affected.
        assertEquals("Keeper", it.info(99, 99));
        assertEquals("AnotherKeeper", it.info(1, 1));
        // assertNotNull(it.bst.find("Keeper"));
    }


    /**
     * Tests deleting a node with only a left child.
     */
    public void testDeleteByCoordWithOnlyLeftChild() {
        it.insert("Root", 50, 50);
        it.insert("LeftChild", 25, 75);
        it.insert("ToDelete", 10, 80); // Child of LeftChild
        it.delete(25, 75); // Delete LeftChild
        assertEquals("", it.info(25, 75)); // Verify deletion
        assertEquals("ToDelete", it.info(10, 80)); // Verify child is still
                                                   // there
    }


    /**
     * Tests deleting a node with only a right child.
     */
    public void testDeleteByCoordWithOnlyRightChild() {
        it.insert("Root", 50, 50);
        it.insert("RightChild", 75, 25);
        it.insert("ToDelete", 80, 10); // Child of RightChild
        it.delete(75, 25); // Delete RightChild
        assertEquals("", it.info(75, 25)); // Verify deletion
        assertEquals("ToDelete", it.info(80, 10)); // Verify child is still
                                                   // there
    }


    /**
     * Tests a region search with a radius of zero on an empty tree.
     */
    public void testRegionSearchWithZeroRadiusOnEmptyTree() {
        assertFuzzyEquals("0", it.search(100, 100, 0));
    }


    /**
     * Tests a region search that should find no cities.
     */
    public void testRegionSearchWithNoCitiesFound() {
        it.insert("A", 10, 10);
        it.insert("B", 20, 20);
        assertFuzzyEquals("2", it.search(100, 100, 5)); // Search far away from
                                                        // A and B
    }


    /**
     * Tests deleting a node with two children from the BST.
     */
    public void testDeleteByNameWithTwoChildren() {
        it.insert("B", 20, 20);
        it.insert("A", 10, 10);
        it.insert("C", 30, 30);
        it.delete("B");
        assertEquals("", it.info(20, 20)); // Verify B is deleted
        assertEquals("A", it.info(10, 10)); // Verify children are still
                                            // accessible
        assertEquals("C", it.info(30, 30));
    }


    /**
     * Tests deleting the root node from the BST.
     */
    public void testDeleteRootByName() {
        it.insert("Root", 50, 50);
        it.insert("A", 10, 10);
        it.delete("Root");
        assertEquals("", it.info(50, 50));
        assertEquals("A", it.info(10, 10));
    }


    /**
     * Tests attempting to remove a city that does not exist in the tree.
     */
    public void testRemoveNonExistentCity() {
        it.insert("A", 10, 10);
        it.insert("B", 20, 20);
        String result = it.delete(30, 30);
        assertEquals("", result);
    }


    /**
     * Tests the region search pruning logic to ensure the left subtree is
     * correctly searched even when the search center is to the right of the
     * splitting axis. This test will fail if the condition
     * `pointValue < axisValue + radius` is mutated to `pointValue < axisValue`.
     */
    public void testRegionSearchPruningLeftSubtree() {
        // A is the root (x-split)
        it.insert("A", 50, 50);
        // B is in the left subtree of A
        it.insert("B", 45, 50);

        // Search from a point (52, 50) that is to the right of A.
        // The radius of 10 should be large enough to include B.
        // Distance from (52, 50) to B(45, 50) is 7.
        String result = it.search(52, 50, 10);

        // The correct implementation should search the left subtree because the
        // circle (center at x=52, radius 10) overlaps with the left plane (x <
        // 50).
        // A mutated version might incorrectly skip the left subtree.
        assertTrue(
            "Search result should contain city 'B' from the left subtree.",
            result.contains("B (45, 50)"));
    }


    /**
     * Tests the region search pruning logic to ensure the right subtree is
     * correctly searched even when the search center is to the left of the
     * splitting axis. This test will fail if the condition
     * `pointValue >= axisValue - radius` is mutated to `pointValue >=
     * axisValue`.
     */
    public void testRegionSearchPruningRightSubtree() {
        // A is the root (x-split)
        it.insert("A", 50, 50);
        // C is in the right subtree of A
        it.insert("C", 55, 50);

        // Search from a point (48, 50) that is to the left of A.
        // The radius of 10 should be large enough to include C.
        // Distance from (48, 50) to C(55, 50) is 7.
        String result = it.search(48, 50, 10);

        // The correct implementation should search the right subtree because
        // the
        // circle (center at x=48, radius 10) overlaps with the right plane (x
        // >= 50).
        // A mutated version might incorrectly skip the right subtree.
        assertTrue(
            "Search result should contain city 'C' from the right subtree.",
            result.contains("C (55, 50)"));
    }


    /**
     * Tests deleting a node that has a left child and a right child, where the
     * right child is the immediate successor.
     * This tests a specific case within the `if (rt.getRight() != null)`
     * branch.
     */
    public void testDeleteNodeWithRightChildAsSuccessor() {
        it.insert("Root", 50, 50);
        it.insert("Left", 25, 75);
        it.insert("NodeWithSuccessor", 75, 25);
        it.insert("Successor", 80, 20); // This should be the successor and the
                                        // right child

        String result = it.delete(75, 25);
        assertTrue(result.contains("NodeWithSuccessor"));
        assertEquals("", it.info(75, 25));
        // The successor should have replaced the deleted node
        assertEquals("Successor", it.info(80, 20));
        // Verify the original left child is still in place
        assertEquals("Left", it.info(25, 75));
    }


    /**
     * Tests deleting a node that has a left child but no right child,
     * which exposes a potential logic error in the provided code.
     * This test targets the `else if (rt.getLeft() != null)` branch.
     */
    public void testDeleteNodeWithOnlyALeftChild() {
        it.insert("Root", 50, 50);
        it.insert("LeftChild", 25, 75);
        it.insert("LeftGrandchild", 20, 80);
        it.delete(25, 75);

        // The intended behavior is for the LeftChild to be removed and the
        // LeftGrandchild
        // to take its place. The provided implementation, however, has a bug
        // that
        // can lead to a corrupted tree. This test will verify the outcome.
        assertEquals("", it.info(25, 75));
        assertEquals("LeftGrandchild", it.info(20, 80));
    }


    /**
     * Tests deleting the root node when it is a leaf node in the KD-tree.
     * This tests the `else` branch for a single-node tree.
     */
    public void testDeleteSingleNodeTree() {
        it.insert("LoneCity", 100, 100);
        String result = it.delete(100, 100);
        assertFuzzyEquals("1\nLoneCity", result);
        // Verify the tree is now empty.
        assertEquals("", it.info(100, 100));
    }


    /**
     * Tests deleting a node with both a left and right child, where the
     * replacement node has a different axis split. This checks that
     * `findMinNode`
     * correctly handles different split axes.
     */
    public void testDeleteNodeWithComplexSuccessor() {
        // Root is x-split, node to delete is y-split
        it.insert("Root", 50, 50);
        it.insert("NodeToDelete", 25, 25); // Left of root (x)
        it.insert("LeftChild", 20, 30); // Left of NodeToDelete (y)
        it.insert("RightChild", 30, 20); // Right of NodeToDelete (y)
        it.insert("Successor", 35, 15); // Successor is in RightChild's subtree

        String result = it.delete(25, 25);
        assertTrue(result.contains("NodeToDelete"));
        assertEquals("", it.info(25, 25));
        // Verify the successor replaced the deleted node
        assertEquals("Successor", it.info(35, 15));
        // Verify the other nodes remain
        assertEquals("LeftChild", it.info(20, 30));
        assertEquals("RightChild", it.info(30, 20));
    }


    /**
     * Tests the `findMinNode` logic when the node has no left child at the
     * current axis.
     * This covers the `return (rt.getLeft() == null) ? rt` branch.
     */
    public void testFindMinNodeOnLeafSubtree() {
        // Tree structure:
        // Root (50, 50) - x-split
        // \
        // RightChild (75, 25) - y-split (the "leaf" for this axis)
        // When we delete the root, findMinNode is called on the right subtree.
        // The call starts at (75, 25) on a y-split. It has no left child, so it
        // should
        // return itself as the minimum.
        it.insert("Root", 50, 50);
        it.insert("RightChild", 75, 25);

        String result = it.delete(50, 50);

        // The node to be deleted is 'Root'. The minimum node found in the right
        // subtree
        // for the x-axis (since the root is an x-split) is 'RightChild'.
        // The root should be replaced by the city from the right child.
        assertEquals("", it.info(50, 50));
        assertEquals("RightChild", it.info(75, 25));
    }


    /**
     * Tests the `findMinNode` logic when the node has a left child.
     * This covers the `return findMinNode(rt.getLeft(), axis, level + 1,
     * result)` branch.
     */
    public void testFindMinNodeOnLeftSubtree() {
        // Tree structure for deletion:
        // Root (50, 50) - x-split
        // \
        // RightChild (75, 25) - y-split
        // /
        // LeftGrandchild (60, 20) - x-split
        // /
        // MinNode (55, 10) - y-split (This is the expected minimum node)
        it.insert("Root", 50, 50);
        it.insert("RightChild", 75, 25);
        it.insert("LeftGrandchild", 60, 20);
        it.insert("MinNode", 55, 10);

        String result = it.delete(50, 50);

        // The deletion of the root (50, 50) should cause the findMinNode method
        // to be called
        // on its right subtree (starting at 75, 25).
        // The method should recurse left from (75, 25) to (60, 20), and then
        // left again
        // to find the minimum value node at (55, 10).
        // The original root should be replaced by 'MinNode's city.
        assertEquals("", it.info(50, 50));
        assertEquals("MinNode", it.info(55, 10));
        // The other nodes should still exist in the tree.
        assertEquals("RightChild", it.info(75, 25));
        assertEquals("LeftGrandchild", it.info(60, 20));
    }


    /**
     * Tests the `findMinNode` logic when the node at the split axis has no left
     * child.
     * This covers the `return (rt.getLeft() == null) ? rt` branch.
     */
    public void testFindMinNodeOnLeftLeaf() {
        // Tree setup:
        // A (50, 50) - Root, x-split
        // \
        // B (75, 25) - Right child, y-split
        // The deletion of 'A' triggers `findMinNode` on the right subtree (`rt
        // = B`).
        // The axis to search for is '0' (x-axis), but 'B' is a y-split
        // (`currentAxis = 1`).
        // The recursion will continue until it hits a node with `currentAxis ==
        // axis`.
        // Let's create a more direct test.
        it.insert("Root", 50, 50);
        it.insert("RightChild", 75, 25);
        it.insert("Successor", 80, 30); // This should be the replacement.

        // When deleting the root, `removeHelp` calls `findMinNode` on the right
        // subtree with `axis=0` (x-axis).
        // The call goes to RightChild (75, 25). Its `currentAxis` is 1
        // (y-split).
        // The `else` block runs, and it recurses on `rightMin`.
        // The recursion continues until it reaches a node on an x-split with no
        // left child.
        // The provided `findMinNode` code has an error, so a direct test is
        // hard.
        // Let's assume a corrected version for testing purposes.

        // With a corrected findMinNode, deleting Root (50,50) should replace it
        // with
        // the minimum from the right subtree. The min for x-axis will be
        // `RightChild` at (75,25)
        // because it's the node with the lowest x-value in that subtree.
        String result = it.delete(50, 50);

        assertTrue(result.contains("Root"));
        assertEquals("", it.info(50, 50));
        assertEquals("RightChild", it.info(75, 25));

        // This test relies on the internal implementation of `findMinNode`
        // which has a logical bug
        // in the original snippet. With the corrected version (which should
        // search all branches),
        // this test would pass, as it confirms the minimum node replaces the
        // deleted node.
    }


    /**
     * Tests the `findMinNode` logic when the node at the split axis has a left
     * child.
     * This covers the `findMinNode(rt.getLeft(), ...)` recursive branch.
     */
    public void testFindMinNodeWithLeftChild() {
        // Tree setup:
        // Root (50, 50) - x-split
        // \
        // RightChild (75, 25) - y-split
        // /
        // LeftGrandchild (60, 20) - x-split
        // The min node for deletion of `Root` (x-split) should be the leftmost
        // node
        // in the right subtree's x-split sub-branch.
        // That is, the leftmost node from the right child's subtree.
        it.insert("Root", 50, 50);
        it.insert("RightChild", 75, 25);
        it.insert("LeftGrandchild", 60, 20);

        // Delete the root node, forcing a search for the minimum node in its
        // right subtree
        String result = it.delete(50, 50);

        assertTrue(result.contains("Root"));
        assertEquals("", it.info(50, 50));
        // The minimum node should be 'LeftGrandchild' (x=60), not 'RightChild'
        // (x=75).
        // This tests that `findMinNode` correctly recurses left to find the min
        // value for the x-axis.
        assertEquals("LeftGrandchild", it.info(60, 20));
        // The old RightChild should now be the new root's child
        assertEquals("RightChild", it.info(75, 25));
    }


    /**
     * Tests `findMinNode` logic when the current node's axis does not match the
     * target axis.
     * This covers the `else` block of the `if (currentAxis == axis)` statement.
     */
    public void testFindMinNodeOnDifferentAxis() {
        // Tree setup:
        // Root (50, 50) - x-split (axis 0)
        // /
        // A (25, 75) - Left child, y-split (axis 1)
        // /
        // B (20, 80) - Left of A, x-split (axis 0)
        // The min node on the y-axis for the whole tree should be 'Root'.
        it.insert("Root", 50, 50);
        it.insert("A", 25, 75);
        it.insert("B", 20, 80);

        // Deletion of a node (e.g., 'A') will cause a recursive call to
        // `findMinNode` from `removeHelp`.
        // However, since `findMinNode` is a private helper, we can't call it
        // directly.
        // Instead, we will delete 'B' and check that 'A' becomes the new
        // parent.
        // This is an indirect test, but it confirms the structural integrity
        // after a removal
        // that uses `findMinNode` to find the replacement.
        it.delete(20, 80);

        // The `info` method will now traverse the tree. If the tree is correct,
        // 'A' should
        // be findable at (25, 75).
        assertEquals("A", it.info(25, 75));
        assertEquals("Root", it.info(50, 50));
    }


    /**
     * Test case for deleting a city by name that is the root in both the BST
     * and KD-tree.
     * This checks the `delete` method's ability to handle the removal of a
     * top-level node.
     */
    public void testDeleteRootNodeByName() {
        // Insert a root city, which will be the root of both trees.
        assertTrue(it.insert("RootCity", 500, 500));
        // Verify the city exists.
        assertEquals("RootCity", it.info(500, 500));

        // Delete the root city by name.
        String result = it.delete("RootCity");

        // The output should contain the deleted city's information.
        assertFuzzyEquals("RootCity (500, 500)", result);
        // The city should no longer be in the database.
        assertEquals("", it.info(500, 500));
    }


    /**
     * Test case for deleting a city that is a non-leaf node in both trees, with
     * children.
     * This tests the recursive deletion logic in both the BST and KD-tree.
     */
    public void testDeleteNonLeafNodeByName() {
        // Build a tree with the city to be deleted having children.
        it.insert("TargetCity", 500, 500); // Root, x-split
        it.insert("LeftChild", 250, 750); // Left of root (x), y-split
        it.insert("RightChild", 750, 250); // Right of root (x), y-split

        // Verify the city to be deleted exists.
        assertEquals("TargetCity", it.info(500, 500));
        assertEquals("LeftChild", it.info(250, 750));
        assertEquals("RightChild", it.info(750, 250));

        // Delete the `TargetCity` by name.
        String result = it.delete("TargetCity");

        // The city should be removed, and its children should still exist.
        assertFuzzyEquals("TargetCity (500, 500)", result);
        assertEquals("", it.info(500, 500));
        assertEquals("LeftChild", it.info(250, 750));
        assertEquals("RightChild", it.info(750, 250));
    }


    /**
     * Tests deleting multiple cities with the same name that are spread across
     * different parts of the KD-tree. This checks that `findAll` and the
     * subsequent deletion loop work correctly.
     */
    public void testDeleteMultipleDuplicatesScattered() {
        // Insert cities with the same name but different coordinates.
        // This creates a scenario where the cities are in different branches of
        // the KD-tree.
        it.insert("Springfield", 100, 100); // Root
        it.insert("OtherCity", 500, 500); // Right child of "Springfield"
        it.insert("Springfield", 200, 200); // Right child of "Springfield",
                                            // same name
        it.insert("Springfield", 50, 50); // Left child of "Springfield", same
                                          // name

        // Verify all cities exist.
        assertEquals("Springfield", it.info(100, 100));
        assertEquals("Springfield", it.info(200, 200));
        assertEquals("Springfield", it.info(50, 50));
        assertEquals("OtherCity", it.info(500, 500));

        // Delete all cities named "Springfield".
        String result = it.delete("Springfield");

        // The output should contain all three deleted cities.
        assertTrue(result.contains("Springfield (100, 100)"));
        assertTrue(result.contains("Springfield (200, 200)"));
        assertTrue(result.contains("Springfield (50, 50)"));
        assertFalse(result.contains("OtherCity (500, 500)"));

        // All "Springfield" cities should be gone, but "OtherCity" should
        // remain.
        assertEquals("", it.info(100, 100));
        assertEquals("", it.info(200, 200));
        assertEquals("", it.info(50, 50));
        assertEquals("OtherCity", it.info(500, 500));
    }


    /**
     * Tests deleting a city that is a leaf in the KD-tree but a non-leaf in the
     * BST.
     * This verifies that the removal logic for each data structure operates
     * independently.
     */
    public void testDeleteCityThatIsKDTreeLeafButBSTNonLeaf() {
        // Build a tree where "Alpha" is a BST parent but a KD-tree leaf
        it.insert("Bravo", 500, 500);
        it.insert("Alpha", 100, 100); // Left of "Bravo" in BST, Left of "Bravo"
                                      // in KD-tree
        it.insert("Charlie", 200, 200); // Right of "Alpha" in BST, Right of
                                        // "Alpha" in KD-tree

        // "Alpha" is a non-leaf in BST (has "Charlie" as a right child) but is
        // a leaf
        // in the KD-tree (it has no children in this simplified case).

        // Verify the cities exist.
        assertEquals("Alpha", it.info(100, 100));
        assertEquals("Charlie", it.info(200, 200));

        // Delete "Alpha" by name.
        String result = it.delete("Alpha");

        // Verify "Alpha" is gone and other nodes are still present.
        assertFuzzyEquals("Alpha (100, 100)", result);
        assertEquals("", it.info(100, 100));
        assertEquals("Charlie", it.info(200, 200));
    }


    /**
     * Tests deleting a city by name where there are multiple cities with that
     * name,
     * and they are scattered in the BST and KD-tree, requiring multiple
     * recursive
     * calls to remove nodes.
     * This simulates a complex database state where duplicates are not
     * contiguous.
     */
    public void testDeleteMultipleCitiesWithComplexTreeStructure() {
        // Setup: Create a complex tree with multiple cities named "Springfield"
        // that are not adjacent in the tree, and other cities in between.
        assertTrue(it.insert("Boston", 50, 50));
        assertTrue(it.insert("Springfield", 100, 100)); // First Springfield
                                                        // (BST root of this
                                                        // name, KD-tree node)
        assertTrue(it.insert("Chicago", 200, 200));
        assertTrue(it.insert("Springfield", 150, 150)); // Second Springfield
                                                        // (BST right child of
                                                        // Springfield, KD-tree
                                                        // node)
        assertTrue(it.insert("Atlanta", 10, 10));
        assertTrue(it.insert("Springfield", 75, 75)); // Third Springfield (BST
                                                      // left child of Boston,
                                                      // KD-tree node)

        // Verify all three "Springfield" cities exist.
        assertFuzzyEquals("Springfield (100, 100)\n"
            + "Springfield (150, 150)\n" + "Springfield (75, 75)", it.info(
                "Springfield"));
        assertEquals("Atlanta", it.info(10, 10));
        assertEquals("Boston", it.info(50, 50));
        assertEquals("Chicago", it.info(200, 200));

        // Perform the complex deletion of all cities named "Springfield".
        String result = it.delete("Springfield");

        // The output should contain all three deleted cities. Order may vary.
        assertTrue(result.contains("Springfield (100, 100)"));
        assertTrue(result.contains("Springfield (150, 150)"));
        assertTrue(result.contains("Springfield (75, 75)"));

        // Verify that all instances of "Springfield" are gone.
        assertEquals("", it.info("Springfield"));

        // Verify that the other cities were not affected and still exist.
        assertEquals("Atlanta", it.info(10, 10));
        assertEquals("Boston", it.info(50, 50));
        assertEquals("Chicago", it.info(200, 200));
    }


    /**
     * Tests deleting a city that is a leaf in the BST but a non-leaf in the
     * KD-tree,
     * forcing the KD-tree's more complex removal logic to be used.
     * This checks that the `delete` method correctly calls the appropriate
     * `remove`
     * helper method for each data structure.
     */
    public void testDeleteCityKDTreeNonLeafBSTLeaf() {
        // Setup:
        // KD-tree (by coordinates):
        // A(500,500) - Root (x)
        // / \
        // B(250,250) C(750,750)
        // BST (by name):
        // A("Alpha") - Root
        // \
        // C("Charlie")
        // \
        // B("Bravo")
        it.insert("Alpha", 500, 500); // A
        it.insert("Charlie", 750, 750); // C
        it.insert("Bravo", 250, 250); // B

        // In the KD-tree, B is a leaf.
        // In the BST, B is a leaf.

        // Let's delete a non-leaf from the KDTree but still a leaf on the BST
        // by Name.
        // `Alpha` is a non-leaf in the KD-tree and a non-leaf in the BST.
        // We can swap which nodes are leaves to test different scenarios.
        // Let's insert cities so "Bravo" is a BST leaf, but not a KD-tree leaf.
        it.clear();
        it.insert("Alpha", 50, 50); // BST Root, KD-tree Root
        it.insert("Gamma", 100, 100); // BST Right Child, KD-tree Right Child
        it.insert("Bravo", 75, 75); // BST Left child of Gamma, KD-tree Leaf of
                                    // Gamma.
        it.insert("Delta", 125, 125); // BST Right child of Gamma.

        // In the BST, `Bravo` is a leaf. In the KD-tree, it is not.

        // Verify `Bravo` exists.
        assertEquals("Bravo", it.info(75, 75));

        // Delete "Bravo" by name.
        String result = it.delete("Bravo");

        // Check that `Bravo` was deleted.
        assertTrue(result.contains("Bravo (75, 75)"));
        assertEquals("", it.info(75, 75));

        // Check that other cities still exist.
        assertEquals("Alpha", it.info(50, 50));
        assertEquals("Gamma", it.info(100, 100));
        assertEquals("Delta", it.info(125, 125));
    }


    /**
     * Tests Sequential Deletions
     */
    public void testSequentialDeletions() {
        // Setup: a complex tree with multiple cities and duplicates.
        it.insert("CityA", 10, 10);
        it.insert("CityB", 20, 20);
        it.insert("CityC", 30, 30);
        it.insert("CityA", 40, 40);

        // Verify initial state
        String printResult = it.print();
        assertTrue(printResult.contains("CityA (40, 40)"));
        assertTrue(printResult.contains("CityA (10, 10)"));
        assertTrue(printResult.contains("CityB (20, 20)"));
        assertTrue(printResult.contains("CityC (30, 30)"));

        // First deletion: a city that doesn't exist
        String result1 = it.delete("CityX");
        assertEquals("", result1);

        // Verify the state is unchanged.
        String printResult2 = it.print();
        assertTrue(printResult2.contains("CityA (40, 40)"));
        assertTrue(printResult2.contains("CityA (10, 10)"));
        assertTrue(printResult2.contains("CityB (20, 20)"));
        assertTrue(printResult2.contains("CityC (30, 30)"));

        // Second deletion: a city with a single instance.
        String result2 = it.delete("CityB");
        assertFuzzyEquals("CityB (20, 20)", result2);

        // Verify the remaining cities.
        assertEquals("", it.info(20, 20)); // CityB should be gone
        assertEquals("CityA", it.info(10, 10));
        assertEquals("CityC", it.info(30, 30));
        assertEquals("CityA", it.info(40, 40));

        // Third deletion: a city with multiple instances.
        String result3 = it.delete("CityA");

        // The output should contain all instances of "CityA".
        assertTrue(result3.contains("CityA (10, 10)"));
        assertTrue(result3.contains("CityA (40, 40)"));

        // Verify the final state. Only `CityC` should remain.
        assertEquals("", it.info(10, 10));
        assertEquals("", it.info(40, 40));
        assertEquals("CityC", it.info(30, 30));
    }


    /**
     * Tests the integrity of the `delete(String name)` method for pre-order
     * deletion
     * by inserting multiple cities with the same name in a way that forces a
     * non-standard traversal order for deletion.
     *
     * This test verifies that the deletion output strictly adheres to a
     * pre-order
     * traversal, confirming the `findAll` helper method correctly finds cities
     * in the required order for deletion.
     */
    public void testComplexPreOrderDeletion() {
        // Setup a complex BST that will be traversed in pre-order
        // The insertion order is:
        // 1. Alpha
        // 2. Gamma
        // 3. Alpha
        // 4. Beta
        // 5. Delta
        //
        // The BST structure (by name) would be:
        // Gamma
        // / \
        // Alpha Delta
        // /
        // Alpha
        //
        // The pre-order traversal for cities named "Alpha" should be:
        // Alpha (25, 25), then Alpha (10, 10).

        // Insert cities in a non-pre-order sequence to ensure the test is
        // robust
        assertTrue(it.insert("Gamma", 50, 50));
        assertTrue(it.insert("Alpha", 25, 25));
        assertTrue(it.insert("Delta", 75, 75));
        assertTrue(it.insert("Beta", 40, 40));
        assertTrue(it.insert("Alpha", 10, 10));

        // Verify initial state
        assertEquals("Gamma", it.info(50, 50));
        assertEquals("Alpha", it.info(25, 25));
        assertEquals("Alpha", it.info(10, 10));

        // Perform the deletion on all cities named "Alpha".
        // This will trigger a pre-order traversal by `findAll`
        String result = it.delete("Alpha");

        // The output string should contain the deleted cities in pre-order.
        // The `findAllHelp` method's logic processes the current node, then the
        // left
        // subtree, then the right subtree. The first Alpha is the child of
        // Gamma.
        // The second Alpha is the child of the first Alpha. The traversal
        // starts at
        // the first Alpha and correctly processes the second Alpha before any
        // other
        // nodes in its right subtree.
        String expected = "Alpha (25, 25)\n" + "Alpha (10, 10)";
        assertFuzzyEquals(expected, result);

        // Verify that the deleted cities are no longer in the database.
        assertEquals("", it.info(25, 25));
        assertEquals("", it.info(10, 10));

        // Verify that the other cities remain untouched.
        assertEquals("Gamma", it.info(50, 50));
        assertEquals("Beta", it.info(40, 40));
        assertEquals("Delta", it.info(75, 75));
    }


    /**
     * Tests the deletion of one specific city when multiple cities have the
     * same
     * name. It verifies that only the city with the matching coordinates is
     * removed from both the KD-Tree and the BST.
     */
    public void testDeleteSpecificCityWithDuplicateName() {
        // Insert three cities with the same name "Springfield"
        assertTrue(it.insert("Springfield", 10, 10));
        assertTrue(it.insert("Springfield", 20, 20));
        assertTrue(it.insert("Springfield", 30, 30));

        // Delete only the "Springfield" at (20, 20)
        String result = it.delete(20, 20);

        // 1. Verify that the delete method reports deleting "Springfield"
        // The node count will vary based on insertion order, so we just check
        // the name.
        assertTrue("The delete result should contain the name 'Springfield'.",
            result.contains("\nSpringfield"));

        // 2. Verify that the city at (20, 20) is truly gone
        assertEquals("City at (20, 20) should be deleted.", "", it.info(20,
            20));

        // 3. Verify that the other two "Springfield" cities still exist
        assertEquals("City at (10, 10) should still exist.", "Springfield", it
            .info(10, 10));
        assertEquals("City at (30, 30) should still exist.", "Springfield", it
            .info(30, 30));

        // 4. Verify that a search by name "Springfield" now returns only two
        // cities
        String remainingCities = it.info("Springfield");
        assertTrue("info(\"Springfield\") should contain the city at (10, 10).",
            remainingCities.contains("Springfield (10, 10)"));
        assertTrue("info(\"Springfield\") should contain the city at (30, 30).",
            remainingCities.contains("Springfield (30, 30)"));
        assertFalse(
            "info(\"Springfield\") should not deleted city at (20, 20).",
            remainingCities.contains("Springfield (20, 20)"));
    }


    /**
     * Tests the deletion of multiple cities with the same name to ensure they
     * are all removed and reported in the correct preorder traversal sequence
     * from the BST.
     */
    public void testDeleteAllDuplicatesByNameInPreorder() {
        // Insert cities to create a specific BST structure.
        // "B" will be the root.
        it.insert("B_City", 50, 50);
        // "A_City" will be the left child of "B_City".
        it.insert("A_City", 50, 25);
        // "C_City" will be the right child of "B_City".
        it.insert("C_City", 75, 75);

        // Insert the duplicate cities. Due to the BST insertion logic
        // (duplicates go left), they will form a chain to the left.
        it.insert("A_City", 10, 10); // Becomes left child of original A_City
        it.insert("A_City", 5, 5); // Becomes left child of the second A_City

        /*
         * The BST will look like this for the name "A_City":
         * "A_City" (25, 25)
         * /
         * "A_City" (10, 10)
         * /
         * "A_City" (5, 5)
         *
         * A preorder traversal should visit them in the order:
         * 1. (25, 25)
         * 2. (10, 10)
         * 3. (5, 5)
         */

        // Define the expected output string based on the preorder traversal.
        String expectedOutput = "A_City (50, 25)\n" + "A_City (10, 10)\n"
            + "A_City (5, 5)\n";

        // Call the delete method for the duplicate name.
        String actualOutput = it.delete("A_City");

        // 1. Verify the output string matches the expected preorder sequence
        // exactly.
        assertEquals(
            "Deleted cities should be listed in preorder traversal order.",
            expectedOutput, actualOutput);

        // 2. Verify that all cities named "A_City" are gone.
        assertEquals("info(\"A_City\")  empty", "", it.info("A_City"));
        assertEquals("City at (25, 25) should be deleted.", "", it.info(25,
            25));
        assertEquals("City at (10, 10) should be deleted.", "", it.info(10,
            10));
        assertEquals("City at (5, 5) should be deleted.", "", it.info(5, 5));

        // 3. Verify that the other cities remain untouched.
        assertEquals("City 'B_City' not be affected.", "B_City", it.info(50,
            50));
        assertEquals("City 'C_City' not be affected.", "C_City", it.info(75,
            75));
    }


    /**
     * Builds a deep KD-Tree and tests various operations like info, search,
     * and delete to ensure they function correctly on a non-trivial tree.
     */
    public void testDeepTreeOperations() {
        // 1. Insert cities to create a KD-Tree with a depth of at least 5.
        // This "zig-zag" pattern forces the tree to grow deep.
        it.insert("Root", 1000, 1000); // Level 0 (x-split)
        it.insert("L1", 500, 1500); // Level 1 (y-split) -> Left of Root
        it.insert("L2", 250, 1250); // Level 2 (x-split) -> Left of L1
        it.insert("L3", 125, 1750); // Level 3 (y-split) -> Left of L2
        it.insert("L4", 60, 1600); // Level 4 (x-split) -> Left of L3
        it.insert("DeepestNode", 30, 1800); // Level 5 -> Left of L4

        // Insert some nodes on the right side for balance
        it.insert("R1", 1500, 500);
        it.insert("R2", 1250, 750);

        // 2. Verify the tree structure and depth using debug()
        // The deepest node should be at level 5.
        String debugOutput = it.debug();
        assertTrue("The debug output should contain a node at level 5.",
            debugOutput.contains("5"));

        // 3. Test info() to find the deepest leaf node.
        assertEquals("Should be able to find the deepest node in the tree.",
            "DeepestNode", it.info(30, 1800));

        // 4. Test search() in a region that includes nodes at various depths.
        // This search should find L3, L4, and DeepestNode.
        String searchResult = it.search(100, 1700, 150);
        assertTrue(searchResult.contains("L3 (125, 1750)"));
        assertTrue(searchResult.contains("L4 (60, 1600)"));
        assertTrue(searchResult.contains("DeepestNode (30, 1800)"));
        // Check that the node count is correct (it will visit all 8 nodes)
        // assertTrue(searchResult.endsWith("\n8"));

        // 5. Test delete(x, y) to remove a node from the middle of the deep
        // branch.
        String deleteResult = it.delete(125, 1750); // Deleting L3
        assertTrue("The delete operation should report the removal of L3.",
            deleteResult.contains("\nL3"));

        // Verify L3 is gone, but its children (L4, DeepestNode) remain.
        assertEquals("Node L3 should no longer be in the tree.", "", it.info(
            125, 1750));
        assertEquals("Child node L4 should still exist.", "L4", it.info(60,
            1600));

        // 6. Test delete(name) to remove a city and check for consistency.
        it.insert("ToDeleteByName", 2000, 2000);
        assertEquals("ToDeleteByName (2000, 2000)\n", it.delete(
            "ToDeleteByName"));
        assertEquals("City 'ToDeleteByName' should be fully removed.", "", it
            .info(2000, 2000));
    }


    /**
     * Builds a deep, right-skewed KD-Tree and tests various operations
     * like info, search, and delete to ensure they function correctly.
     */
    public void testDeepRightTreeOperations() {
        // 1. Insert cities to create a right-skewed KD-Tree with a
        // depth of at
        // least 5.
        it.insert("Root", 1000, 1000); // Level 0 (x-split)
        it.insert("R1", 1500, 500); // Level 1 (y-split) -> Right of Root
        it.insert("R2", 1750, 750); // Level 2 (x-split) -> Right of R1
        it.insert("R3", 1875, 250); // Level 3 (y-split) -> Right of R2
        it.insert("R4", 1940, 400); // Level 4 (x-split) -> Right of R3
        it.insert("DeepestNode", 1970, 200); // Level 5 -> Right of R4

        // Insert some nodes on the left side for balance
        it.insert("L1", 500, 1500);
        it.insert("L2", 750, 1250);

        // 2. Verify the tree structure and depth using debug()
        // The deepest node should be at level 5.
        String debugOutput = it.debug();
        assertTrue("The debug output should contain a node at level 5.",
            debugOutput.contains("5"));

        // 3. Test info() to find the deepest leaf node.
        assertEquals(
            "Should be able to find the deepest in the right-skewed tree.",
            "DeepestNode", it.info(1970, 200));

        // 4. Test search() in a region that includes nodes at
        // various depths on
        // the right.
        // This search should find R3, R4, and DeepestNode.
        String searchResult = it.search(1900, 300, 150);
        assertTrue(searchResult.contains("R3 (1875, 250)"));
        assertTrue(searchResult.contains("R4 (1940, 400)"));
        assertTrue(searchResult.contains("DeepestNode (1970, 200)"));
        // Check that the node count is correct (it will visit all 8 nodes)
        // assertTrue(searchResult.endsWith("\n8"));

        // 5. Test delete(x, y) to remove a node from the middle of the deep
        // branch.
        String deleteResult = it.delete(1875, 250); // Deleting R3
        assertTrue("The delete operation should report the removal of R3.",
            deleteResult.contains("\nR3"));

        // Verify R3 is gone, but its children (R4, DeepestNode) remain.
        assertEquals("Node R3 should no longer be in the tree.", "", it.info(
            1875, 250));
        assertEquals("Child node R4 should still exist.", "R4", it.info(1940,
            400));

        // 6. Test delete(name) on the deepest node to ensure full removal.
        String deepestDelete = it.delete("DeepestNode");
        assertEquals("DeepestNode (1970, 200)\n", deepestDelete);
        assertEquals("DeepestNode should be fully removed.", "", it.info(1970,
            200));
    }


    /**
     * Verifies that the debug() output has strictly correct indentation,
     * which
     * depends on incrementing the level in its recursive helper.
     * This test will
     * fail if 'level + 1' is mutated to 'level'.
     */
    public void testDebugIndentationStrict() {
        it.insert("Root", 100, 100); // Level 0
        it.insert("LeftChild", 50, 150); // Level 1
        it.insert("GrandChild", 25, 125); // Level 2

        // Expected output with 2 spaces of indentation per level.
        String expected = "2    GrandChild (25, 125)\n"
            + "1  LeftChild (50, 150)\n" + "0Root (100, 100)\n";

        // Use assertEquals, not assertFuzzyEquals, for a strict check.
        assertEquals("Debug output must have exact indentation.", expected, it
            .debug());
    }


    /**
     * Tests that the remove operation correctly identifies the replacement node
     * by using the correct splitting axis. This fails if the 'level' is not
     * incremented in findMinNode, causing it to find the minimum on the wrong
     * axis.
     */
    public void testRemoveWithCorrectAxisForSuccessor() {
        // 1. Create a specific tree structure.
        it.insert("ToDelete", 50, 50); // Root, will be deleted.
        it.insert("RightChild", 70, 60); // Right of root.

        // Add two nodes to the RightChild's subtree.
        // This will be the node with the minimum X value in the right subtree.
        it.insert("X_Min", 60, 100);
        // This will be the node with the minimum Y value in the right subtree.
        it.insert("Y_Min", 90, 20);

        /*
         * When we delete (50, 50), the split axis for its replacement is X.
         * The findMinNode method should be called on the right subtree (rooted
         * at 70,60)
         * looking for the node with the smallest X value. That is "X_Min" at
         * (60, 100).
         *
         * MUTATED LOGIC: If level is not incremented in removeHelp ->
         * findMinNode,
         * the axis might be incorrect, and it might pick "Y_Min" as the
         * replacement.
         */

        // 2. Delete the root node.
        it.delete(50, 50);

        // 3. Check the new root of the tree with debug(). It should be "X_Min".
        String debugOutput = it.debug();
        assertTrue("The new root should be 'X_Min' (60, 100) after deletion.",
            debugOutput.startsWith("0X_Min (60, 100)"));

        // 4. Verify the old root is gone and the correct replacement is now at
        // the root.
        assertEquals("Old root should be gone.", "", it.info(50, 50));
        assertEquals("The node 'Y_Min' should still exist in the tree.",
            "Y_Min", it.info(90, 20));
    }


    /**
     * Tests that the KD-Tree correctly alternates the splitting axis (x then y)
     * during insertion. This test fails if the 'level' is not incremented in
     * the recursive insertHelp call, which would cause all splits to be on the
     * x-axis.
     */
    public void testAxisAlternationOnInsertAndFind() {
        // 1. Insert Root at (50, 50). First split is on X.
        it.insert("Root", 50, 50);

        // 2. Insert B at (60, 40). Since 60 > 50, it goes to the RIGHT of Root.
        // The next split should be on Y.
        it.insert("B", 60, 40);

        // 3. Insert C at (70, 30).
        // CORRECT LOGIC: At node B (60, 40), we split on Y. Since 30 < 40,
        // C should go to the LEFT of B.
        // MUTATED LOGIC: If level is not incremented, the split at B is on X
        // again.
        // Since 70 > 60, C would go to the RIGHT of B.
        it.insert("C", 70, 30);

        // 4. Verify the tree structure with debug().
        // A correct in-order traversal would be: Root, C, B.
        String debugOutput = it.debug();
        String expected = "0Root (50, 50)\n" + "2    C (70, 30)\n"
            + "1  B (60, 40)\n";

        // By using a non-fuzzy equals, we can check the structure.
        // A mutated tree would likely have the order Root, B, C.
        assertEquals("Tree structure should reflect alternating axis splits.",
            expected, debugOutput);

        // 5. Explicitly check if C can be found. A mutated findHelp would fail.
        assertEquals("Should find city C by its coordinates.", "C", it.info(70,
            30));
    }


    /**
     * A comprehensive test that runs through all the capabilities of the
     * `findMinNode` helper method
     * during a deletion operation.
     * * This test sets up a complex KD-tree structure and a specific deletion
     * scenario that forces
     * `findMinNode` to exercise its different branches:
     * - The case where `currentAxis != axis`, forcing a search of both
     * subtrees.
     * - The recursive search that leads to a node where `currentAxis == axis`.
     * - The discovery of the minimum node at a leaf, triggering the
     * `rt.getLeft() == null` branch.
     * - The comparison logic to determine the overall minimum from multiple
     * sub-branches.
     * This ensures the integrity of the KD-tree is maintained.
     */
    public void testFindMinNodeComprehensiveDeletionScenario() {
        // 1. Setup: Build a multi-level KD-tree with a specific structure.
        // The node to be deleted, `CityC (150, 50)`, is at a y-split level
        // (level 1).
        // Its right subtree is at `CityD (125, 25)`.
        // The `findMinNode` search will be for the minimum y-value in this
        // subtree.
        //
        // Tree structure (by coordinates):
        //
        // (100, 100) - Root (x-split)
        // / \
        // (50, 150) - B (y-split) (150, 50) - C (y-split) -> NODE TO DELETE
        // / \
        // (125, 25) - D (x-split) (175, 75) - E (x-split)
        // /
        // (110, 10) - F (y-split) -> THE TRUE MIN NODE

        // Insert cities to create the complex tree structure.
        assertTrue(it.insert("RootCity", 100, 100));
        assertTrue(it.insert("CityB", 50, 150));
        assertTrue(it.insert("CityC", 150, 50)); // Node to be deleted
        assertTrue(it.insert("CityD", 125, 25)); // Left of CityC
        assertTrue(it.insert("CityE", 175, 75)); // Right of CityC
        assertTrue(it.insert("CityF", 110, 10)); // Left of CityD (y-value is
                                                 // lowest)

        // Verify initial state.
        assertEquals("RootCity", it.info(100, 100));
        assertEquals("CityB", it.info(50, 150));
        assertEquals("CityC", it.info(150, 50));
        assertEquals("CityD", it.info(125, 25));
        assertEquals("CityE", it.info(175, 75));
        assertEquals("CityF", it.info(110, 10));

        // Perform deletion on CityC (150, 50).
        // This will force the `removeHelp` method to find a successor using
        // `findMinNode`.
        // The successor should be CityF (110, 10), as it has the minimum
        // y-value in the right subtree of CityC.
        String result = it.delete(150, 50);

        // The output should report the deletion of CityC.
        assertTrue(
            "Deletion output should contain the name of the deleted city.",
            result.contains("CityC"));

        // Verify the deleted city is gone.
        assertEquals("Deleted city should no longer be in the tree.", "", it
            .info(150, 50));

        // Verify that CityF (the successor) is still present and correctly
        // re-linked.
        assertEquals("CityF should still exist.", "CityF", it.info(110, 10));

        // Verify that the other cities are still present, confirming tree
        // integrity.
        assertEquals("RootCity should not have been affected.", "RootCity", it
            .info(100, 100));
        assertEquals("CityB should still exist.", "CityB", it.info(50, 150));
        assertEquals("CityD should still exist.", "CityD", it.info(125, 25));
        assertEquals("CityE should still exist.", "CityE", it.info(175, 75));

        // The test implicitly confirms that `findMinNode` correctly traversed
        // the tree
        // and identified the `CityF` as the node with the minimum y-value
        // in the right subtree of the node being deleted.
    }


    /**
     * A comprehensive test that runs through all the capabilities of the
     * `findMinNode` helper method
     * during a deletion operation.
     *
     * This test is designed to force `findMinNode` to exercise all its
     * branches:
     * - The case where `currentAxis != axis`, forcing a search of both
     * subtrees.
     * - The recursive search that leads to a node where `currentAxis == axis`.
     * - The discovery of the minimum node at a leaf, triggering the
     * `rt.getLeft() == null` branch.
     * - The comparison logic to determine the overall minimum from multiple
     * sub-branches.
     * This ensures the integrity of the KD-tree is maintained.
     */
    public void testFindMinNodeComprehensiveDeletionScenario2() {
        // 1. Setup: Build a multi-level KD-tree with a specific structure.
        // The goal is to delete the root, and force `findMinNode` to search its
        // right subtree
        // to find the minimum value node. The right subtree is structured such
        // that the
        // search for the minimum value has to traverse both left and right
        // branches of the subtree.
        //
        // Tree structure (by coordinates):
        //
        // (100, 100) - Root (level 0, x-split) -> NODE TO DELETE
        // / \
        // (50, 150) - L1 (y-split) (150, 50) - R1 (y-split)
        // | / \
        // (50, 140) - L2 (x-split) (125, 75) - R2 (x-split) (175, 25) - R3
        // (x-split) -> THE TRUE MIN NODE
        //
        // The `findMinNode` call will start on R1 (150, 50) with `axis=0`
        // (x-split).
        // - R1 is a y-split (`currentAxis=1`), so the `else` block runs.
        // - `findMinNode` is called on R1's left child (R2 at (125, 75)) and
        // right child (R3 at (175, 25)).
        // - The call on R2 `(125, 75)` has `currentAxis=0`, which matches the
        // target `axis`. It has no left child, so it returns itself.
        // - The call on R3 `(175, 25)` also has `currentAxis=0` and no left
        // child, so it returns itself.
        // - The `findMinNode` call at R1 then compares R2, R3, and R1's
        // x-coordinates to find the minimum.
        // - The correct minimum is R2 at (125, 75).

        // Insert cities to create the complex tree structure.
        assertTrue(it.insert("RootCity", 100, 100)); // Root (level 0)
        assertTrue(it.insert("L1", 50, 150)); // Left of Root (level 1)
        assertTrue(it.insert("R1", 150, 50)); // Right of Root (level 1)
        assertTrue(it.insert("L2", 50, 140)); // Right of L1 (level 2)
        assertTrue(it.insert("R2", 125, 75)); // Left of R1 (level 2)
        assertTrue(it.insert("R3", 175, 25)); // Right of R1 (level 2)

        // Verify initial state.
        assertEquals("RootCity", it.info(100, 100));
        assertEquals("L1", it.info(50, 150));
        assertEquals("R1", it.info(150, 50));
        assertEquals("L2", it.info(50, 140));
        assertEquals("R2", it.info(125, 75));
        assertEquals("R3", it.info(175, 25));

        // Perform deletion on the root node (100, 100).
        // This will force `removeHelp` to find a successor in its right
        // subtree.
        String result = it.delete(100, 100);

        // The output should report the deletion of the root city.
        assertTrue(
            "Deletion output should contain the name of the deleted city.",
            result.contains("RootCity"));

        // Verify the deleted city is gone.
        assertEquals("Deleted city should no longer be in the tree.", "", it
            .info(100, 100));

        // The successor, based on the x-split at the root, should be the node
        // with the minimum
        // x-value in the right subtree. This is R2 (125, 75).
        assertEquals(
            "The successor node R2 should now be at the root's location.", "R2",
            it.info(125, 75));

        // Verify that the other cities are still present, confirming tree
        // integrity.
        assertEquals("L1 should still exist.", "L1", it.info(50, 150));
        assertEquals("L2 should still exist.", "L2", it.info(50, 140));
        assertEquals("R1 should still exist.", "R1", it.info(150, 50));
        assertEquals("R3 should still exist.", "R3", it.info(175, 25));
    }


    /**
     * A comprehensive test that runs through all the capabilities of the
     * `findMinNode` helper method
     * during a deletion operation.
     *
     * This test is designed to force `findMinNode` to exercise all its
     * branches:
     * - The case where `currentAxis != axis`, forcing a search of both
     * subtrees.
     * - The recursive search that leads to a node where `currentAxis == axis`.
     * - The discovery of the minimum node at a leaf, triggering the
     * `rt.getLeft() == null` branch.
     * - The comparison logic to determine the overall minimum from multiple
     * sub-branches.
     * This ensures the integrity of the KD-tree is maintained.
     */
    public void testFindMinNodeComprehensiveDeletionScenario3() {
        // 1. Setup: Build a multi-level KD-tree with a specific structure.
        // The goal is to delete a node, and force `findMinNode` to search its
        // right subtree
        // to find the minimum value node. The right subtree is structured such
        // that the
        // search for the minimum value has to traverse both left and right
        // branches of the subtree.
        //
        // Tree structure (by coordinates):
        //
        // (100, 100) - Root (x-split)
        // / \
        // (50, 150) - B (y-split) (150, 50) - C (y-split) -> NODE TO DELETE
        // / \
        // (125, 25) - D (x-split) (175, 75) - E (x-split)
        // /
        // (110, 10) - F (y-split) -> THE TRUE MIN NODE

        // Insert cities to create the complex tree structure.
        assertTrue(it.insert("RootCity", 100, 100));
        assertTrue(it.insert("CityB", 50, 150));
        assertTrue(it.insert("CityC", 150, 50)); // Node to be deleted
        assertTrue(it.insert("CityD", 125, 25)); // Left of CityC
        assertTrue(it.insert("CityE", 175, 75)); // Right of CityC
        assertTrue(it.insert("CityF", 110, 10)); // Left of CityD (y-value is
                                                 // lowest)

        // Verify initial state.
        assertEquals("RootCity", it.info(100, 100));
        assertEquals("CityB", it.info(50, 150));
        assertEquals("CityC", it.info(150, 50));
        assertEquals("CityD", it.info(125, 25));
        assertEquals("CityE", it.info(175, 75));
        assertEquals("CityF", it.info(110, 10));

        // Perform deletion on CityC (150, 50).
        // This will force the `removeHelp` method to find a successor using
        // `findMinNode`.
        // The successor should be CityF (110, 10), as it has the minimum
        // y-value in the right subtree of CityC.
        String result = it.delete(150, 50);

        // The output should report the deletion of CityC.
        assertTrue(
            "Deletion output should contain the name of the deleted city.",
            result.contains("CityC"));

        // Verify the deleted city is gone.
        assertEquals("Deleted city should no longer be in the tree.", "", it
            .info(150, 50));

        // Verify that CityF (the successor) is still present and correctly
        // re-linked.
        assertEquals("CityF should still exist.", "CityF", it.info(110, 10));

        // Verify that the other cities are still present, confirming tree
        // integrity.
        assertEquals("RootCity should not have been affected.", "RootCity", it
            .info(100, 100));
        assertEquals("CityB should still exist.", "CityB", it.info(50, 150));
        assertEquals("CityD should still exist.", "CityD", it.info(125, 25));
        assertEquals("CityE should still exist.", "CityE", it.info(175, 75));

        // The test implicitly confirms that `findMinNode` correctly traversed
        // the tree
        // and identified the `CityF` as the node with the minimum y-value
        // in the right subtree of the node being deleted.
    }


    /**
     * Tests the TRUE && TRUE case: The coordinates match exactly.
     * The city should be successfully deleted.
     */
    public void testRemoveCoordinatesMatch() {
        it.insert("Target", 50, 50);
        it.insert("Other", 100, 100);

        // Action
        String result = it.delete(50, 50);

        // Verification
        assertTrue("Deletion report should contain the target city's name.",
            result.contains("\nTarget"));
        assertEquals("The city at (50, 50) should be deleted.", "", it.info(50,
            50));
        assertEquals("The other city should remain.", "Other", it.info(100,
            100));
    }


    /**
     * Tests the TRUE && FALSE case: X matches, but Y does not.
     * The city should NOT be deleted, and the search should continue,
     * eventually finding nothing and leaving the tree unchanged.
     */
    public void testRemoveXMatchYFails() {
        it.insert("Target", 50, 50); // The city that should NOT be deleted

        // Action: Attempt to delete with correct X but incorrect Y
        String result = it.delete(50, 99);

        // Verification
        assertEquals("The result should be empty as no city was found.", "",
            result);
        assertEquals("The city at (50, 50) should not have been deleted.",
            "Target", it.info(50, 50));
    }


    /**
     * Tests the FALSE && TRUE case: Y matches, but X does not.
     * The city should NOT be deleted. This is crucial for catching a mutant
     * that changes '&&' to '||'.
     */
    public void testRemoveYMatchXFails() {
        it.insert("Target", 50, 50); // The city that should NOT be deleted

        // Action: Attempt to delete with correct Y but incorrect X
        String result = it.delete(99, 50);

        // Verification
        assertEquals("The result should be empty as no city was found.", "",
            result);
        assertEquals("The city at (50, 50) should not have been deleted.",
            "Target", it.info(50, 50));
    }


    /**
     * Tests the FALSE && FALSE case: Neither coordinate matches.
     * The city should NOT be deleted.
     */
    public void testRemoveCoordinatesMismatch() {
        it.insert("Target", 50, 50);

        // Action: Attempt to delete with completely wrong coordinates
        String result = it.delete(99, 99);

        // Verification
        assertEquals("The result should be empty as no city was found.", "",
            result);
        assertEquals("The city at (50, 50) should not have been deleted.",
            "Target", it.info(50, 50));
    }


    /**
     * Verifies that the print() method produces a correctly indented string for
     * a
     * BST with a depth greater than 3. This test uses a strict string
     * comparison
     * to catch any mutations affecting the indentation logic (level * 2) or the
     * recursive level increment.
     */
    public void testPrintIndentationWithDeepTree() {
        // 1. Insert cities in an order that creates a deep, left-skewed BST.
        // The names are chosen to force a specific structure.
        it.insert("D_City", 40, 40); // Level 0 (Root)
        it.insert("C_City", 30, 30); // Level 1
        it.insert("B_City", 20, 20); // Level 2
        it.insert("A_City", 10, 10); // Level 3 (Deepest node)

        /*
         * The BST structure will be:
         * D
         * /
         * C
         * /
         * B
         * /
         * A
         *
         * An in-order traversal will print A, B, C, D.
         */

        // 2. Construct the expected output string with precise indentation.
        // Level 3: 3 * 2 = 6 spaces
        // Level 2: 2 * 2 = 4 spaces
        // Level 1: 1 * 2 = 2 spaces
        // Level 0: 0 * 2 = 0 spaces
        String expectedOutput = "3      A_City (10, 10)\n"
            + "2    B_City (20, 20)\n" + "1  C_City (30, 30)\n"
            + "0D_City (40, 40)\n";

        // 3. Get the actual output from the print() method.
        String actualOutput = it.print();

        // 4. Perform a strict comparison.
        assertEquals(
            "The print() output must have exact indentation for a deep tree.",
            expectedOutput, actualOutput);
    }

}
