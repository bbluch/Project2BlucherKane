// -------------------------------------------------------------------------
/**
 * Contains logic and implementation for the KDTree.
 * 
 * @author benblucher, austink23
 * @version Oct 8, 2025
 */
public class KDTree {
    // ~ Fields ................................................................

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
    private BSTNode root;
    private int nodeCount;

    /**
     * Constructs an empty KDTree.
     */
    public KDTree() {
        root = null;
        setNodeCount(0);
    }


    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        setNodeCount(0);
    }


    /**
     * Inserts a city into the KD Tree.
     *
     * @param city
     *            The city to insert.
     */
    public void insert(City city) {
        root = insertHelp(root, city, 0);
        setNodeCount(size() + 1);
    }


    /**
     * Removes a city from the KD Tree.
     *
     * @param x
     *            The x-coordinate of the city to remove.
     * @param y
     *            The y-coordinate of the city to remove.
     * @return The removed city, or null if not found.
     */
    public SearchResult remove(int x, int y) {
        SearchResult sr = new SearchResult();
        root = removeHelp(root, x, y, 0, sr);
        if (sr.getResults().length > 0) {
            nodeCount--;
        }
// City temp = find(x, y);
// if (temp != null) {
// root = removeHelp(root, x, y, 0);
// setNodeCount(size() - 1);
// }
        return sr;
    }


    /**
     * Finds a city by its coordinates.
     *
     * @param x
     *            The x-coordinate of the city.
     * @param y
     *            The y-coordinate of the city.
     * @return The city if found, otherwise null.
     */
    public City find(int x, int y) {
        return findHelp(root, x, y, 0);
    }


    /**
     * Performs a region search to find all cities within a given radius of a
     * point.
     *
     * @param x
     *            The x-coordinate of the center of the search circle.
     * @param y
     *            The y-coordinate of the center of the search circle.
     * @param radius
     *            The radius of the search circle.
     * @return An array of cities within the specified region.
     */
// public String regionSearch(int x, int y, int radius) {
// SearchResult results = new SearchResult();
// int count = regionSearchHelp(root, x, y, radius, 0, results);
// String strFinal = "";
// City[] cities = results.getResults();
//
// for (int i = 0; i < cities.length; i++) {
// strFinal = strFinal + cities[i].getName();
// strFinal = strFinal + " (" + cities[i].getX();
// strFinal = strFinal + ", " + cities[i].getY() + ")\n";
// }
//
// strFinal = strFinal + count;
// return strFinal;
//
// }

    public SearchResult regionSearch(int x, int y, int radius) {
        SearchResult result = new SearchResult();
        regionSearchHelp(root, x, y, radius, 0, result);
        return result;
    }


    /**
     * Performs a region search to find all cities within a given radius of a
     * point.
     *
     * @param x
     *            The x-coordinate of the center of the search circle.
     * @param y
     *            The y-coordinate of the center of the search circle.
     * @return An array of cities within the specified region.
     */
    public String regionSearch2(int x, int y) {
        SearchResult results = new SearchResult();
        regionSearchHelp(root, x, y, 0, 0, results);
        String strFinal = "";
        City[] cities = results.getResults();
        // count++;

        strFinal += results.nodesVisited + "\n" + cities[0].getName() + "\n";
// for (int i = 0; i < cities.length; i++) {
// strFinal = strFinal + cities[i].getName() + "\n";
// }

        return strFinal;

    }


    /**
     * Helper method for insert logic.
     * 
     * @param rt
     *            root node
     * @param city
     *            The city being inserted
     * @param level
     *            The level of tree
     * @return node being inserted
     */
// private BSTNode insertHelp(BSTNode rt, City city, int level) {
// if (rt == null) {
// return new BSTNode(city);
// }
//
//// int axis = level % 2;
// if (level % 2 == 0) { // Compare x-coordinates
// if (city.getX() < rt.getCity().getX()) {
// rt.setLeft(insertHelp(rt.getLeft(), city, level + 1));
// }
// else {
// rt.setRight(insertHelp(rt.getRight(), city, level + 1));
// }
// }
// else { // Compare y-coordinates
// if (city.getY() < rt.getCity().getY()) {
// rt.setLeft(insertHelp(rt.getLeft(), city, level + 1));
// }
// else {
// rt.setRight(insertHelp(rt.getRight(), city, level + 1));
// }
// }
// return rt;
// }

    private BSTNode insertHelp(BSTNode rt, City city, int level) {
        if (rt == null)
            return new BSTNode(city);
        int axis = level % 2;
        int cityCoord = (axis == 0) ? city.getX() : city.getY();
        int nodeCoord = (axis == 0) ? rt.getCity().getX() : rt.getCity().getY();
        if (cityCoord < nodeCoord) {
            rt.setLeft(insertHelp(rt.getLeft(), city, level + 1));
        }
        else {
            rt.setRight(insertHelp(rt.getRight(), city, level + 1));
        }
        return rt;
    }


    /**
     * Helper method for find method.
     * 
     * @param rt
     *            root node
     * @param x
     *            The x-coordinate of the city
     * @param y
     *            The y-coordinate of the city
     * @param level
     *            The level of the tree
     * @return the city if found, null if not
     */
// private City findHelp(BSTNode rt, int x, int y, int level) {
// if (rt == null) {
// return null;
// }
// if (rt.getCity().getX() == x && rt.getCity().getY() == y) {
// return rt.getCity();
// }
//
// int axis = level % 2;
// if (axis == 0) { // Compare x-coordinates
// if (x < rt.getCity().getX()) {
// return findHelp(rt.getLeft(), x, y, level + 1);
// }
// return findHelp(rt.getRight(), x, y, level + 1);
// } // Compare y-coordinates
// if (y < rt.getCity().getY()) {
// return findHelp(rt.getLeft(), x, y, level + 1);
// }
//
// return findHelp(rt.getRight(), x, y, level + 1);
// }

    private City findHelp(BSTNode rt, int x, int y, int level) {
        if (rt == null)
            return null;
        if (rt.getCity().getX() == x && rt.getCity().getY() == y)
            return rt.getCity();
        int axis = level % 2;
        int pointCoord = (axis == 0) ? x : y;
        int nodeCoord = (axis == 0) ? rt.getCity().getX() : rt.getCity().getY();
        if (pointCoord < nodeCoord) {
            return findHelp(rt.getLeft(), x, y, level + 1);
        }
        return findHelp(rt.getRight(), x, y, level + 1);
    }


    /**
     * Helper method for remove logic.
     *
     * @param rt
     *            root node
     * @param x
     *            The x-coordinate for the city
     * @param y
     *            The y-coordnate for the city
     * @param level
     *            The level of the tree
     * @return node being removed, null if not found
     */
// private BSTNode removeHelp(BSTNode rt, int x, int y, int level) {
// if (rt == null) {
// return null;
// }
// int axis = level % 2;
// int compare;
// SearchResult sr = new SearchResult();
//
// if (rt.getCity().getX() == x && rt.getCity().getY() == y) {
// if (rt.getRight() != null) {
// City min = findMin(rt.getRight(), axis, level + 1);
// rt.setCity(min);
// rt.setRight(removeHelp(rt.getRight(), min.getX(), min.getY(),
// level + 1));
// }
// else if (rt.getLeft() != null) {
// City min = findMin(rt.getLeft(), axis, level + 1);
// rt.setCity(min);
// rt.setRight(removeHelp(rt.getLeft(), min.getX(), min.getY(),
// level + 1));
// rt.setLeft(null); // The left subtree is now empty
// }
// else { // Leaf node
// return null;
// }
// return rt;
// }
//
// if (axis == 0) {
// compare = x - rt.getCity().getX();
// }
// else {
// compare = y - rt.getCity().getY();
// }
//
// if (compare < 0) {
// rt.setLeft(removeHelp(rt.getLeft(), x, y, level + 1));
//
// }
// else {
// rt.setRight(removeHelp(rt.getRight(), x, y, level + 1));
// }
//
// return rt;
// }

    private BSTNode removeHelp(
        BSTNode rt,
        int x,
        int y,
        int level,
        SearchResult result) {
        if (rt == null)
            return null;
        result.nodesVisited++;

        int axis = level % 2;

        if (rt.getCity().getX() == x && rt.getCity().getY() == y) {
            result.add(rt.getCity());
            if (rt.getRight() != null) {
                BSTNode minNode = findMinNode(rt.getRight(), axis, level + 1,
                    result);
                rt.setCity(minNode.getCity());
                rt.setRight(removeHelp(rt.getRight(), minNode.getCity().getX(),
                    minNode.getCity().getY(), level + 1, result));
            }
            else if (rt.getLeft() != null) {
                BSTNode minNode = findMinNode(rt.getLeft(), axis, level + 1,
                    result);
                rt.setCity(minNode.getCity());
                rt.setRight(removeHelp(rt.getLeft(), minNode.getCity().getX(),
                    minNode.getCity().getY(), level + 1, result));
                rt.setLeft(null);
             // Corrected logic: Replace the current node with its left child
                //return rt.getLeft();
            }
            else {
                return null;
            }
        }
        else {
            int pointCoord = (axis == 0) ? x : y;
            int nodeCoord = (axis == 0)
                ? rt.getCity().getX()
                : rt.getCity().getY();
            if (pointCoord < nodeCoord) {
                rt.setLeft(removeHelp(rt.getLeft(), x, y, level + 1, result));
            }
            else {
                rt.setRight(removeHelp(rt.getRight(), x, y, level + 1, result));
            }
        }
        return rt;
    }


    /**
     * Finds the node with minimum value.
     *
     * @param rt
     *            root node
     * @param axis
     *            The determining count for comparison
     * @param level
     *            The level of the tree
     * @return The city with minimum value
     */
    private BSTNode findMinNode(
        BSTNode rt,
        int axis,
        int level,
        SearchResult result) {
        if (rt == null)
            return null;
        result.nodesVisited++; // Count node visit in findMin as well

        int currentAxis = level % 2;

        if (currentAxis == axis) {
            return (rt.getLeft() == null)
                ? rt
                : findMinNode(rt.getLeft(), axis, level + 1, result);
        }

        // int axis = level % 2;
        BSTNode leftMin = findMinNode(rt.getLeft(), axis, level + 1, result);
        BSTNode rightMin = findMinNode(rt.getRight(), axis, level + 1, result);
        BSTNode min = rt;

        if (leftMin != null && compareCities(leftMin.getCity(), min.getCity(),
            axis) < 0) {
            min = leftMin;
        }
        if (rightMin != null && compareCities(rightMin.getCity(), min.getCity(),
            axis) < 0) {
            min = rightMin;
        }
        return min;
    }

// private BSTNode removeHelp(
// BSTNode rt,
// int x,
// int y,
// int level,
// int[] visitedCount,
// City[] removedCity) {
// if (rt == null) {
// return null;
// }
//
// visitedCount[0]++; // Count every node we visit.
// int axis = level % 2;
//
// if (rt.getCity().getX() == x && rt.getCity().getY() == y) {
// removedCity[0] = rt.getCity();
// if (rt.getRight() != null) {
// BSTNode minNode = findMinNode(rt.getRight(), axis, level + 1);
// rt.setCity(minNode.getCity());
// rt.setRight(removeHelp(rt.getRight(), minNode.getCity().getX(),
// minNode.getCity().getY(), level + 1, visitedCount,
// new City[1]));
// }
// else if (rt.getLeft() != null) {
// BSTNode minNode = findMinNode(rt.getLeft(), axis, level + 1);
// rt.setCity(minNode.getCity());
// rt.setRight(removeHelp(rt.getLeft(), minNode.getCity().getX(),
// minNode.getCity().getY(), level + 1, visitedCount,
// new City[1]));
// rt.setLeft(null);
// }
// else {
// return null;
// }
// }
// else {
// int pointCoord = (axis == 0) ? x : y;
// int nodeCoord = (axis == 0)
// ? rt.getCity().getX()
// : rt.getCity().getY();
// if (pointCoord < nodeCoord) {
// rt.setLeft(removeHelp(rt.getLeft(), x, y, level + 1,
// visitedCount, removedCity));
// }
// else {
// rt.setRight(removeHelp(rt.getRight(), x, y, level + 1,
// visitedCount, removedCity));
// }
// }
// return rt;
// }

// private BSTNode findMinNode(BSTNode rt, int axis, int level) {
// if (rt == null)
// return null;
//
// int currentAxis = level % 2;
// if (currentAxis == axis) {
// if (rt.getLeft() == null)
// return rt;
// return findMinNode(rt.getLeft(), axis, level + 1);
// }
//
// BSTNode leftMin = findMinNode(rt.getLeft(), axis, level + 1);
// BSTNode rightMin = findMinNode(rt.getRight(), axis, level + 1);
// BSTNode min = rt;
//
// int minVal = (axis == 0) ? min.getCity().getX() : min.getCity().getY();
//
// if (leftMin != null) {
// int leftVal = (axis == 0)
// ? leftMin.getCity().getX()
// : leftMin.getCity().getY();
// if (leftVal < minVal) {
// min = leftMin;
// minVal = leftVal;
// }
// }
// if (rightMin != null) {
// int rightVal = (axis == 0)
// ? rightMin.getCity().getX()
// : rightMin.getCity().getY();
// if (rightVal < minVal) {
// min = rightMin;
// }
// }
// return min;
// }


    /**
     * Compares two cities, returning based on KDTree logic.
     *
     * @param c1
     *            First city being compared.
     * @param c2
     *            Second city being compared.
     * @param axis
     *            The determining comparison count.
     * @return result of comparison
     */
    private int compareCities(City c1, City c2, int axis) {
        if (axis == 0) {
            return Integer.compare(c1.getX(), c2.getX());
        }

        return Integer.compare(c1.getY(), c2.getY());

    }


    /**
     * Helper method to search the KDTree.
     * 
     * @param rt
     *            root node
     * @param x
     *            The x-coordinate of the city
     * @param y
     *            The y-coordinate of the city
     * @param radius
     *            The radius being examined
     * @param level
     *            The level of the tree
     * @param results
     *            The results of the region search
     * @return int
     *         number of nodes visited
     */
// private int regionSearchHelp(
// BSTNode rt,
// int x,
// int y,
// int radius,
// int level,
// SearchResult results) {
// if (rt == null) {
// return 0;
// }
//
// int count = 1;
// // Check if the current city is within the radius
// double distance = Math.sqrt(Math.pow(rt.getCity().getX() - x, 2) + Math
// .pow(rt.getCity().getY() - y, 2));
// if (distance <= radius) {
// results.add(rt.getCity());
// }
// int axis = level % 2;
// int pointValue = (axis == 0) ? x : y;
// int axisValue = (axis == 0) ? rt.getCity().getX() : rt.getCity().getY();
//
// // Check if the search circle intersects with the "left" (or "lower")
// // subtree region.
// // The circle intersects if the search point's coordinate is within the
// // radius of the
// // splitting plane on the left side.
// if (pointValue < axisValue + radius) {
// count += regionSearchHelp(rt.getLeft(), x, y, radius, level + 1,
// results);
// }
//
// // Check if the search circle intersects with the "right" (or "upper")
// // subtree region.
// // The circle intersects if the search point's coordinate is within the
// // radius of the
// // splitting plane on the right side.
// if (pointValue > axisValue - radius) {
// count += regionSearchHelp(rt.getRight(), x, y, radius, level + 1,
// results);
// }
//
// return count;

    private void regionSearchHelp(
        BSTNode rt,
        int x,
        int y,
        int radius,
        int level,
        SearchResult result) {
        if (rt == null)
            return;
        result.nodesVisited++;
        double distance = Math.sqrt(Math.pow(rt.getCity().getX() - x, 2) + Math
            .pow(rt.getCity().getY() - y, 2));
        if (distance <= radius)
            result.add(rt.getCity());
        int axis = level % 2;
        int pointValue = (axis == 0) ? x : y;
        int axisValue = (axis == 0) ? rt.getCity().getX() : rt.getCity().getY();
        if (pointValue < axisValue + radius)
            regionSearchHelp(rt.getLeft(), x, y, radius, level + 1, result);
        if (pointValue >= axisValue - radius)
            regionSearchHelp(rt.getRight(), x, y, radius, level + 1, result);
    }


    /**
     * Returns a string representation of the KD-Tree via an in-order traversal.
     * 
     * @return The formatted string.
     */
    public String getInOrderTraversal() {
        StringBuilder sb = new StringBuilder();
        inOrderHelp(root, 0, sb);
        return sb.toString();
    }


    /**
     * Recursive helper for the in-order traversal.
     * 
     * @param rt
     *            The current node.
     * @param level
     *            The current level in the tree.
     * @param sb
     *            The StringBuilder to append to.
     */
    private void inOrderHelp(BSTNode rt, int level, StringBuilder sb) {
        if (rt == null) {
            return;
        }
        // In-order: Left, Root, Right
        inOrderHelp(rt.getLeft(), level + 1, sb);

        // Append level and indentation
        sb.append(level);
        for (int i = 0; i < level * 2; i++) {
            sb.append(" ");
        }

        // Append city info and a newline
        sb.append(rt.getCity().toString()).append("\n");

        inOrderHelp(rt.getRight(), level + 1, sb);
    }


    // ----------------------------------------------------------
    /**
     * Get the current value of nodeCount.
     * 
     * @return The value of nodeCount for this object.
     */
    public int size() {
        return nodeCount;
    }


    // ----------------------------------------------------------
    /**
     * Set the value of nodeCount for this object.
     * 
     * @param nodeCount
     *            The new value for nodeCount.
     */
    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }
}
