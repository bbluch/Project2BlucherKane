// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here.
 * Follow it with additional details about its purpose, what abstraction
 * it represents, and how to use it.
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
        nodeCount = 0;
    }


    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        nodeCount = 0;
    }


    /**
     * Inserts a city into the KD Tree.
     *
     * @param city
     *            The city to insert.
     */
    public void insert(City city) {
        root = insertHelp(root, city, 0);
        nodeCount++;
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
    public City remove(int x, int y) {
        City temp = find(x, y);
        if (temp != null) {
            root = removeHelp(root, x, y, 0);
            nodeCount--;
        }
        return temp;
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
     * A helper class to manage the dynamic array for region search results.
     */
    private static class RegionSearchResult {
        City[] cities;
        int count;

        RegionSearchResult() {
            cities = new City[10]; // Initial capacity
            count = 0;
        }


        void add(City city) {
            if (count == cities.length) {
                // Resize the array if it's full
                City[] newCities = new City[cities.length * 2];
                System.arraycopy(cities, 0, newCities, 0, cities.length);
                cities = newCities;
            }
            cities[count++] = city;
        }


        City[] getResults() {
            // Trim the array to the actual size
            City[] trimmedCities = new City[count];
            System.arraycopy(cities, 0, trimmedCities, 0, count);
            return trimmedCities;
        }
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
    public City[] regionSearch(int x, int y, int radius) {
        RegionSearchResult results = new RegionSearchResult();
        regionSearchHelp(root, x, y, radius, 0, results);
        return results.getResults();
    }


    private BSTNode insertHelp(BSTNode rt, City city, int level) {
        if (rt == null) {
            return new BSTNode(city);
        }

        int axis = level % 2;
        if (axis == 0) { // Compare x-coordinates
            if (city.getX() < rt.getCity().getX()) {
                rt.setLeft(insertHelp(rt.getLeft(), city, level + 1));
            }
            else {
                rt.setRight(insertHelp(rt.getRight(), city, level + 1));
            }
        }
        else { // Compare y-coordinates
            if (city.getY() < rt.getCity().getY()) {
                rt.setLeft(insertHelp(rt.getLeft(), city, level + 1));
            }
            else {
                rt.setRight(insertHelp(rt.getRight(), city, level + 1));
            }
        }
        return rt;
    }


    private City findHelp(BSTNode rt, int x, int y, int level) {
        if (rt == null) {
            return null;
        }
        if (rt.getCity().getX() == x && rt.getCity().getY() == y) {
            return rt.getCity();
        }

        int axis = level % 2;
        if (axis == 0) { // Compare x-coordinates
            if (x < rt.getCity().getX()) {
                return findHelp(rt.getLeft(), x, y, level + 1);
            }
            return findHelp(rt.getRight(), x, y, level + 1);
        } // Compare y-coordinates
        if (y < rt.getCity().getY()) {
            return findHelp(rt.getLeft(), x, y, level + 1);
        }

        return findHelp(rt.getRight(), x, y, level + 1);
    }


    private BSTNode removeHelp(BSTNode rt, int x, int y, int level) {
        if (rt == null) {
            return null;
        }

        int axis = level % 2;
        int compare;

        if (rt.getCity().getX() == x && rt.getCity().getY() == y) {
            if (rt.getRight() != null) {
                City min = findMin(rt.getRight(), axis, level + 1);
                rt.setCity(min);
                rt.setRight(removeHelp(rt.getRight(), min.getX(), min.getY(),
                    level + 1));
            }
            else if (rt.getLeft() != null) {
                City min = findMin(rt.getLeft(), axis, level + 1);
                rt.setCity(min);
                rt.setRight(removeHelp(rt.getLeft(), min.getX(), min.getY(),
                    level + 1));
                rt.setLeft(null); // The left subtree is now empty
            }
            else { // Leaf node
                return null;
            }
            return rt;
        }

        if (axis == 0) {
            compare = x - rt.getCity().getX();
        }
        else {
            compare = y - rt.getCity().getY();
        }

        if (compare < 0) {
            rt.setLeft(removeHelp(rt.getLeft(), x, y, level + 1));
        }
        else {
            rt.setRight(removeHelp(rt.getRight(), x, y, level + 1));
        }

        return rt;
    }


    private City findMin(BSTNode rt, int axis, int level) {
        if (rt == null) {
            return null;
        }

        int currentAxis = level % 2;

        if (currentAxis == axis) {
            if (rt.getLeft() == null) {
                return rt.getCity();
            }
            return findMin(rt.getLeft(), axis, level + 1);
        }
        City leftMin = findMin(rt.getLeft(), axis, level + 1);
        City rightMin = findMin(rt.getRight(), axis, level + 1);
        City rootCity = rt.getCity();

        City min = rootCity;
        if (leftMin != null && compareCities(leftMin, min, axis) < 0) {
            min = leftMin;
        }
        if (rightMin != null && compareCities(rightMin, min, axis) < 0) {
            min = rightMin;
        }
        return min;
    }


    private int compareCities(City c1, City c2, int axis) {
        if (axis == 0) {
            return Integer.compare(c1.getX(), c2.getX());
        }

        return Integer.compare(c1.getY(), c2.getY());

    }


    private void regionSearchHelp(
        BSTNode rt,
        int x,
        int y,
        int radius,
        int level,
        RegionSearchResult results) {
        if (rt == null) {
            return;
        }

        // Check if the current city is within the radius
        double distance = Math.sqrt(Math.pow(rt.getCity().getX() - x, 2) + Math
            .pow(rt.getCity().getY() - y, 2));
        if (distance <= radius) {
            results.add(rt.getCity());
        }

        int axis = level % 2;
        int axisValue;
        int pointValue;

        if (axis == 0) {
            axisValue = rt.getCity().getX();
            pointValue = x;
        }
        else {
            axisValue = rt.getCity().getY();
            pointValue = y;
        }

        // Recursively search the appropriate subtrees
        if (pointValue < axisValue) {
            regionSearchHelp(rt.getLeft(), x, y, radius, level + 1, results);
            if (axisValue - pointValue <= radius) {
                regionSearchHelp(rt.getRight(), x, y, radius, level + 1,
                    results);
            }
        }
        else {
            regionSearchHelp(rt.getRight(), x, y, radius, level + 1, results);
            if (pointValue - axisValue <= radius) {
                regionSearchHelp(rt.getLeft(), x, y, radius, level + 1,
                    results);
            }
        }
    }
}
