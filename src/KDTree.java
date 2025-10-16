// -------------------------------------------------------------------------
/**
 * Contains logic and implementation for the KDTree.
 * The kd tree is a modification to the BST that allows for efficient processing
 * of multi-dimensional search keys. The kd tree differs from the BST in that
 * each level of the kd tree makes branching decisions based on a particular
 * search key associated with that level, called the discriminator. This
 * is used with the city coordinates for this project.
 * 
 * @author benblucher, austink23
 * @version Oct 8, 2025
 */
public class KDTree {
    // ~ Fields ................................................................

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
    private BSTNode root;
    // private int nodeCount;

    /**
     * Constructs an empty KDTree.
     */
    public KDTree() {
        root = null;
        // setNodeCount(0);
    }


    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        // setNodeCount(0);
    }


    /**
     * Inserts a city into the KD Tree.
     *
     * @param city
     *            The city to insert.
     */
    public void insert(City city) {
        root = insertHelp(root, city, 0);
        // setNodeCount(size() + 1);
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

    private BSTNode insertHelp(BSTNode rt, City city, int level) {
        if (rt == null)
            return new BSTNode(city);
        int axis = level++ % 2;
        int cityCoord = (axis == 0) ? city.getX() : city.getY();
        int nodeCoord = (axis == 0) ? rt.getCity().getX() : rt.getCity().getY();
        if (cityCoord < nodeCoord) {
            rt.setLeft(insertHelp(rt.getLeft(), city, level));
        }
        else {
            rt.setRight(insertHelp(rt.getRight(), city, level));
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
    private City findHelp(BSTNode rt, int x, int y, int level) {
        if (rt == null)
            return null;
        if (rt.getCity().getX() == x && rt.getCity().getY() == y)
            return rt.getCity();
        int axis = level++ % 2;
        int pointCoord = (axis == 0) ? x : y;
        int nodeCoord = (axis == 0) ? rt.getCity().getX() : rt.getCity().getY();
        if (pointCoord < nodeCoord) {
            return findHelp(rt.getLeft(), x, y, level);
        }
        return findHelp(rt.getRight(), x, y, level);
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

    private BSTNode removeHelp(
        BSTNode rt,
        int x,
        int y,
        int level,
        SearchResult result) {
        if (rt == null)
            return null;
        result.nodesVisited++;

        int axis = level++ % 2;

        if (rt.getCity().getX() == x && rt.getCity().getY() == y) {
            result.add(rt.getCity());
            if (rt.getRight() != null) {
                BSTNode minNode = findMinNode(rt.getRight(), axis, level,
                    result);
                rt.setCity(minNode.getCity());
                rt.setRight(removeHelp(rt.getRight(), minNode.getCity().getX(),
                    minNode.getCity().getY(), level, result));
            }
            else if (rt.getLeft() != null) {
                BSTNode minNode = findMinNode(rt.getLeft(), axis, level,
                    result);
                rt.setCity(minNode.getCity());
                rt.setRight(removeHelp(rt.getLeft(), minNode.getCity().getX(),
                    minNode.getCity().getY(), level, result));
                rt.setLeft(null);
                // Corrected logic: Replace the
                // current node with its left child
                // return rt.getLeft();
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
                rt.setLeft(removeHelp(rt.getLeft(), x, y, level, result));
            }
            else {
                rt.setRight(removeHelp(rt.getRight(), x, y, level, result));
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

        int currentAxis = level++ % 2;

        if (currentAxis == axis) {
            return (rt.getLeft() == null)
                ? rt
                : findMinNode(rt.getLeft(), axis, level, result);
        }

        // int axis = level % 2;
        BSTNode leftMin = findMinNode(rt.getLeft(), axis, level, result);
        BSTNode rightMin = findMinNode(rt.getRight(), axis, level, result);
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
        else {
            return Integer.compare(c1.getY(), c2.getY());
        }


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
     */

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
        int axis = level++ % 2;
        int pointValue = (axis == 0) ? x : y;
        int axisValue = (axis == 0) ? rt.getCity().getX() : rt.getCity().getY();
        if (pointValue < axisValue + radius)
            regionSearchHelp(rt.getLeft(), x, y, radius, level, result);
        if (pointValue >= axisValue - radius)
            regionSearchHelp(rt.getRight(), x, y, radius, level, result);
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
}
