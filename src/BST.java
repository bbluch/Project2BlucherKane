// -------------------------------------------------------------------------

/**
 * Contains the logic and implementation for the binary search tree.A binary
 * search tree (BST) is a binary tree that conforms to the following condition,
 * known as the binary search tree property. All nodes stored in the left
 * subtree of a node whose key value is K
 * have key values less than or equal to K.
 * 
 * 
 * @author benblucher, austink23
 * @version Oct 8, 2025
 */
public class BST {
    private BSTNode root;

    /**
     * Constructs an empty BST.
     */
    public BST() {
        root = null;
    }

    // ----------------------------------------------------------
    /**
     * Remove method to remove a specific city based on names and
     * coordinates
     * 
     * @param city
     *            City being removed
     * @param x
     *            X coordinate of city
     * @param y
     *            Y coordinate of city
     * @return city being removed
     */
    public City remove(City city, int x, int y) {
        // Retrieve the node that is being removed
        root = removeHelp(root, city.getName(), city.getX(), city.getY());
        // ensure it is not null
        if (root == null)
            return null;
        // return city for node
        return root.getCity();
    }


    /**
     * Helper method when deleting a specific city.
     * 
     * @param rt
     *            root node
     * @param name
     *            Name of city
     * @param x
     *            X coordinate of city
     * @param y
     *            Y coordinate of city
     * @return node that is going to be removed
     */
    private BSTNode removeHelp(BSTNode rt, String name, int x, int y) {
        // compare the cities' name to decide where to go
        if (rt.getCity().getName().compareTo(name) > 0) {
            rt.setLeft(removeHelp(rt.getLeft(), name, x, y));
        }

        // go right if the name of the rt city is before the parameter city
        else if (rt.getCity().getName().compareTo(name) < 0) {
            rt.setRight(removeHelp(rt.getRight(), name, x, y));
        }
        // if they have same name, check coordinates for potential match
        else if (rt.getCity().getX() == x && rt.getCity().getY() == y) {

            // replace with right node if left is null
            if (rt.getLeft() == null) {
                return rt.getRight();
            }
            // replace with left node if right is null
            else if (rt.getRight() == null) {
                return rt.getLeft();
            }
            // Two children
            else {
                // find max of two to replace it
                BSTNode temp = getMax(rt.getLeft());
                rt.setCity(temp.getCity());
                rt.setLeft(deleteMax(rt.getLeft()));
            }
        }
        else {
            // Names match, but coordinates don't.
            // Keep searching down the left.
            rt.setLeft(removeHelp(rt.getLeft(), name, x, y));
        }
        return rt;
    }


    // ----------------------------------------------------------
    /**
     * Insert a city into the BST.
     * 
     * @param city
     *            The city being inserted
     */
    // Make sure your nodeCount is updated in insert()
    public void insert(City city) {
        root = insertHelp(root, city);
    }


    /**
     * Returns a string representation of the BST via an in-order traversal.
     * 
     * @return The formatted string.
     */
    public String getInOrderTraversal() {
        // stringbuilder for easy format
        StringBuilder sb = new StringBuilder();
        // call helper method for logic
        inOrderHelp(root, 0, sb);
        // return stringbuilder contents
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
        // null check
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


    /**
     * Finds all cities with the given name.
     *
     * @param name
     *            The name of the city to find.
     * @return An array of all matching City objects. Returns an empty array if
     *         no matches are found.
     */
    public City[] findAll(String name) {
        // Object to hold necessary cities and nodes visited
        SearchResult results = new SearchResult();

        // helper method call
        findAllHelp(root, name, results);

        // return results from SearchResult method
        return results.getResults();
    }


    /**
     * Recursive helper method to find all cities with a given name.
     *
     * @param rt
     *            The current node in the traversal.
     * @param name
     *            The name to search for.
     * @param results
     *            The collection of matching cities.
     */
    private void findAllHelp(BSTNode rt, String name, SearchResult results) {
        if (rt == null) {
            return; // Base case: end of a branch
        }

        // retrieve name of city
        String currentName = rt.getCity().getName();
        // compare the cities, store result
        int compare = name.compareTo(currentName);

        if (compare == 0) {
            // We found a match! Add it to our results.
            results.add(rt.getCity());
            // Since duplicates are inserted to the left, we must
            // continue searching the left subtree for more matches.
            findAllHelp(rt.getLeft(), name, results);
            findAllHelp(rt.getRight(), name, results);

        }
        else if (compare < 0) {
            // The name we are looking for is smaller than the current node's
            // name,
            // so we only need to search the left subtree.
            findAllHelp(rt.getLeft(), name, results);
        }
        else { // compare > 0
               // The name we are looking for is larger, so we only need to
               // search
               // the right subtree.
            findAllHelp(rt.getRight(), name, results);
        }
    }


    /**
     * Private helper method for the insert method.
     * 
     * @param rt
     *            root node
     * @param city
     *            city being inserted
     * @return node returned
     */
    private BSTNode insertHelp(BSTNode rt, City city) {
        // null check
        if (rt == null) {
            return new BSTNode(city);
        }
        // explore left subtree
        if (rt.getCity().getName().compareTo(city.getName()) >= 0) {
            // Equal values go to the left
            rt.setLeft(insertHelp(rt.getLeft(), city));
        }
        else {
            // explore right subtree
            rt.setRight(insertHelp(rt.getRight(), city));
        }
        return rt;
    }


    /**
     * Gets the node with max value.
     *
     * @param rt
     *            root node
     * @return node with max
     */
    private BSTNode getMax(BSTNode rt) {
        // null check
        if (rt.getRight() == null) {
            return rt;
        }
        // recursive call to find the max node using BST logic
        return getMax(rt.getRight());
    }


    /**
     * Deletes the max node.
     *
     * @param rt
     *            root node
     * @return node with max
     */
    private BSTNode deleteMax(BSTNode rt) {
        // null check
        if (rt.getRight() == null) {
            return rt.getLeft();
        }
        // deletes max node using BST logic (going down right subtrees)
        rt.setRight(deleteMax(rt.getRight()));
        return rt;
    }
}
