// -------------------------------------------------------------------------
/**
 * Contains the implementation for both BSTNode and KDNodes.
 * 
 * @author benblucher, austink23
 * @version Oct 7, 2025
 */
public class BSTNode {

    private City city;
    private BSTNode left;
    private BSTNode right;

    /**
     * Constructs a new GISNode.
     *
     * @param city
     *            The city data for this node.
     */
    public BSTNode(City city) {
        this.city = city;
        this.left = null;
        this.right = null;
    }


    /**
     * Gets the city object.
     *
     * @return The city.
     */
    public City getCity() {
        return city;
    }


    /**
     * Sets the city object.
     *
     * @param city
     *            The new city.
     */
    public void setCity(City city) {
        this.city = city;
    }


    /**
     * Gets the left child of the node.
     *
     * @return The left child.
     */
    public BSTNode getLeft() {
        return left;
    }


    /**
     * Sets the left child of the node.
     *
     * @param left
     *            The new left child.
     */
    public void setLeft(BSTNode left) {
        this.left = left;
    }


    /**
     * Gets the right child of the node.
     *
     * @return The right child.
     */
    public BSTNode getRight() {
        return right;
    }


    /**
     * Sets the right child of the node.
     *
     * @param right
     *            The new right child.
     */
    public void setRight(BSTNode right) {
        this.right = right;
    }
}
