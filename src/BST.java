// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here.
 * Follow it with additional details about its purpose, what abstraction
 * it represents, and how to use it.
 * 
 * @author benblucher, austink23
 * @version Oct 8, 2025
 */
public class BST {
    // ~ Fields ................................................................

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
    private BSTNode root;
    private int nodeCount;

    /**
     * Constructs an empty BST.
     */
    public BST() {
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
     * Inserts a city into the BST.
     *
     * @param city
     *            The city to insert.
     */
    public void insert(City city) {
        root = insertHelp(root, city);
        nodeCount++;
    }


    /**
     * Removes a city with the given name from the BST.
     *
     * @param name
     *            The name of the city to remove.
     * @return The City object of the removed node, or null if not found.
     */
    public City remove(String name) {
        City temp = find(name);
        if (temp != null) {
            root = removeHelp(root, name);
            nodeCount--;
        }
        return temp;
    }


    /**
     * Finds a city by its name.
     *
     * @param name
     *            The name of the city to find.
     * @return The City object if found, otherwise null.
     */
    public City find(String name) {
        return findHelp(root, name);
    }


    public City find(int x, int y) {
        return findHelp(root, x, y);
    }


    private City findHelp(BSTNode rt, int x, int y) {
        if (rt == null)
            return null;
        
        // TODO Auto-generated method stub
        return null;
    }


    /**
     * Gets the number of nodes in the tree.
     *
     * @return The node count.
     */
    public int size() {
        return nodeCount;
    }


    private City findHelp(BSTNode rt, String name) {
        if (rt == null) {
            return null;
        }
        if (rt.getCity().getName().compareTo(name) > 0) {
            return findHelp(rt.getLeft(), name);
        }
        else if (rt.getCity().getName().equals(name)) {
            return rt.getCity();
        }
        else {
            return findHelp(rt.getRight(), name);
        }
    }


    private BSTNode insertHelp(BSTNode rt, City city) {
        if (rt == null) {
            return new BSTNode(city);
        }
        if (rt.getCity().getName().compareTo(city.getName()) >= 0) { // Equal
                                                                     // values
                                                                     // go to
                                                                     // the left
            rt.setLeft(insertHelp(rt.getLeft(), city));
        }
        else {
            rt.setRight(insertHelp(rt.getRight(), city));
        }
        return rt;
    }


    private BSTNode removeHelp(BSTNode rt, String name) {
        if (rt == null) {
            return null;
        }
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


    private BSTNode getMax(BSTNode rt) {
        if (rt.getRight() == null) {
            return rt;
        }
        return getMax(rt.getRight());
    }


    private BSTNode deleteMax(BSTNode rt) {
        if (rt.getRight() == null) {
            return rt.getLeft();
        }
        rt.setRight(deleteMax(rt.getRight()));
        return rt;
    }
}
