// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here.
 * Follow it with additional details about its purpose, what abstraction
 * it represents, and how to use it.
 * 
 * @author benblucher, austink23
 * @version Oct 7, 2025
 * @param <K>
 * @param <V>
 */
public class BSTNode<K, V> {
    // ~ Fields ................................................................

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
    private K key;
    private V value;
    private BSTNode<K, V> left;
    private BSTNode<K, V> right;

    /**
     * Constructs a new BSTNode.
     *
     * @param key
     *            The key of the node.
     * @param value
     *            The value of the node.
     */
    public BSTNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
    }


    /**
     * Gets the key of the node.
     *
     * @return The key.
     */
    public K getKey() {
        return key;
    }


    /**
     * Gets the value of the node.
     *
     * @return The value.
     */
    public V getValue() {
        return value;
    }


    /**
     * Sets the value of the node.
     *
     * @param value
     *            The new value.
     */
    public void setValue(V value) {
        this.value = value;
    }


    /**
     * Gets the left child of the node.
     *
     * @return The left child.
     */
    public BSTNode<K, V> getLeft() {
        return left;
    }


    /**
     * Sets the left child of the node.
     *
     * @param left
     *            The new left child.
     */
    public void setLeft(BSTNode<K, V> left) {
        this.left = left;
    }


    /**
     * Gets the right child of the node.
     *
     * @return The right child.
     */
    public BSTNode<K, V> getRight() {
        return right;
    }


    /**
     * Sets the right child of the node.
     *
     * @param right
     *            The new right child.
     */
    public void setRight(BSTNode<K, V> right) {
        this.right = right;
    }


    /**
     * Checks if the node is a leaf node.
     *
     * @return True if the node is a leaf, false otherwise.
     */
    public boolean isLeaf() {
        return (left == null) && (right == null);
    }
}
