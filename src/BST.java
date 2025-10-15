// -------------------------------------------------------------------------

/**
 * Contains the logic and implementation for the binary search tree.
 * 
 * @author benblucher, austink23
 * @version Oct 8, 2025
 */
public class BST {
    // ~ Fields ................................................................

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
    private BSTNode root;

    /**
     * Constructs an empty BST.
     */
    public BST() {
        root = null;
    }


    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
    }


    /**
     * Inserts a city into the BST.
     *
     * @param city
     *            The city to insert.
     */
//    public void insert(City city) {
//        root = insertHelp(root, city);
//        nodeCount++;
//    }

    /**
     * Removes a city with the given name from the BST.
     * Note: This will remove only the first occurrence found.
     *
     * @param name The name of the city to remove.
     * @return The City object of the removed node, or null if not found.
     */
//    public City remove(String name) {
//        City temp = find(name);
//        if (temp != null) {
//            City[] removed = new City[1];
//            root = removeHelp(root, temp, removed);
//            if (removed[0] != null) {
//                nodeCount--;
//            }
//        }
//        return temp;
//    }

    /**
     * Removes a specific city from the BST. This is more precise than
     * removing by name, as it targets an exact city object.
     *
     * @param city The city to remove.
     * @return The removed City object, or null if not found.
     */
    public City remove(City city) {
        /*
         * if (city == null) {
         * return null;
         * }
         */
        //City[] removed = new City[1];
        root = removeHelp(root, city.getName());
        if (root == null)
            return null;
        return root.getCity();
    }
    
    
    // ----------------------------------------------------------
    /**
     * Insert a city into the BST.
     * @param city The city being inserted
     */
    // Make sure your nodeCount is updated in insert()
    public void insert(City city) {
        root = insertHelp(root, city);
    }

    /**
     * Removes a city with the given name from the BST.
     *
     * @param name
     *            The name of the city to remove.
     * @return The City object of the removed node, or null if not found.
     */
    /*
     * public City remove(String name) {
     * City temp = find(name);
     * // if (temp != null) {
     * // root = removeHelp(root, name);
     * // }
     * return temp;
     * }
     */


    /**
     * Returns a string representation of the BST via an in-order traversal.
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


    /**
     * Finds a city by its name.
     *
     * @param name
     *            The name of the city to find.
     * @return The City object if found, otherwise null.
     */
    /*
     * public City find(String name) {
     * return findHelp(root, name);
     * }
     */



    /**
     * Finds all cities with the given name.
     *
     * @param name
     *            The name of the city to find.
     * @return An array of all matching City objects. Returns an empty array if
     *         no matches are found.
     */
    public City[] findAll(String name) {
        SearchResult results = new SearchResult();
        findAllHelp(root, name, results);
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

        String currentName = rt.getCity().getName();
        int compare = name.compareTo(currentName);

        if (compare == 0) {
            // We found a match! Add it to our results.
            results.add(rt.getCity());
            // Since duplicates are inserted to the left, we must
            // continue searching the left subtree for more matches.
            findAllHelp(rt.getLeft(), name, results);
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
     * Private helper method to assist in find method.
     *
     * @param rt
     *            root node
     * @param name
     *            name of city
     * @return city found
     */
    /*
     * private City findHelp(BSTNode rt, String name) {
     * if (rt.getCity().getName().compareTo(name) > 0) {
     * return findHelp(rt.getLeft(), name);
     * }
     * else if (rt.getCity().getName().equals(name)) {
     * return rt.getCity();
     * }
     * else {
     * return findHelp(rt.getRight(), name);
     * }
     * }
     */

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
        if (rt == null) {
            return new BSTNode(city);
        }
        if (rt.getCity().getName().compareTo(city.getName()) >= 0) {
            // Equal values go to the left
            rt.setLeft(insertHelp(rt.getLeft(), city));
        }
        else {
            rt.setRight(insertHelp(rt.getRight(), city));
        }
        return rt;
    }


    /**
     * Private helper method for remove.
     *
     * @param rt
     *            root node
     * @param name
     *            name of city being removed
     * @return node returned
     */
    private BSTNode removeHelp(BSTNode rt, String name) {
        /*
         * if (rt == null) {
         * return null;
         * }
         */
        if (rt.getCity().getName().compareTo(name) > 0) {
            rt.setLeft(removeHelp(rt.getLeft(), name));
        }
        else if (rt.getCity().getName().compareTo(name) < 0) {
            rt.setRight(removeHelp(rt.getRight(), name));
        }
        else { // Found it
            if (rt.getLeft() == null) {
                return rt.getRight();
            }
            else if (rt.getRight() == null) {
                return rt.getLeft();
            }
            else { // Two children
                BSTNode temp = getMax(rt.getLeft());
                rt.setCity(temp.getCity());
                rt.setLeft(deleteMax(rt.getLeft()));
            }
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
        if (rt.getRight() == null) {
            return rt;
        }
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
        if (rt.getRight() == null) {
            return rt.getLeft();
        }
        rt.setRight(deleteMax(rt.getRight()));
        return rt;
    }
}
