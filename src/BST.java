// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here.
 * Follow it with additional details about its purpose, what abstraction
 * it represents, and how to use it.
 * 
 * @author benblucher, austink23
 * @version Oct 8, 2025
 * @param <K>
 * @param <V>
 */
public class BST<K extends Comparable<K>, V> {
    // ~ Fields ................................................................

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
    private BSTNode<K, V> root;
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
     * Inserts a key-value pair into the BST.
     *
     * @param key
     *            The key to insert.
     * @param value
     *            The value to insert.
     */
    public void insert(K key, V value) {
        root = insertHelp(root, key, value);
        nodeCount++;
    }


    /**
     * Removes a key from the BST.
     *
     * @param key
     *            The key to remove.
     * @return The value of the removed key, or null if not found.
     */
    public V remove(K key) {
        V temp = findHelp(root, key);
        if (temp != null) {
            root = removeHelp(root, key);
            nodeCount--;
        }
        return temp;
    }


    /**
     * Finds the value associated with a given key.
     *
     * @param key
     *            The key to find.
     * @return The value associated with the key, or null if not found.
     */
    public V find(K key) {
        return findHelp(root, key);
    }


    /**
     * Gets the number of nodes in the tree.
     *
     * @return The node count.
     */
    public int size() {
        return nodeCount;
    }


    private V findHelp(BSTNode<K, V> rt, K key) {
        if (rt == null) {
            return null;
        }
        if (rt.getKey().compareTo(key) > 0) {
            return findHelp(rt.getLeft(), key);
        }
        else if (rt.getKey().compareTo(key) == 0) {
            return rt.getValue();
        }
        else {
            return findHelp(rt.getRight(), key);
        }
    }


    private BSTNode<K, V> insertHelp(BSTNode<K, V> rt, K key, V value) {
        if (rt == null) {
            return new BSTNode<>(key, value);
        }
        if (rt.getKey().compareTo(key) >= 0) { // Equal values go to the left
            rt.setLeft(insertHelp(rt.getLeft(), key, value));
        }
        else {
            rt.setRight(insertHelp(rt.getRight(), key, value));
        }
        return rt;
    }


    private BSTNode<K, V> removeHelp(BSTNode<K, V> rt, K key) {
        if (rt == null) {
            return null;
        }
        if (rt.getKey().compareTo(key) > 0) {
            rt.setLeft(removeHelp(rt.getLeft(), key));
        }
        else if (rt.getKey().compareTo(key) < 0) {
            rt.setRight(removeHelp(rt.getRight(), key));
        }
        else { // Found it
            if (rt.getLeft() == null) {
                return rt.getRight();
            }
            else if (rt.getRight() == null) {
                return rt.getLeft();
            }
            else { // Two children
                BSTNode<K, V> temp = getMax(rt.getLeft());
                rt.setValue(temp.getValue());
                rt.setLeft(deleteMax(rt.getLeft()));
            }
        }
        return rt;
    }


    private BSTNode<K, V> getMax(BSTNode<K, V> rt) {
        if (rt.getRight() == null) {
            return rt;
        }
        return getMax(rt.getRight());
    }


    private BSTNode<K, V> deleteMax(BSTNode<K, V> rt) {
        if (rt.getRight() == null) {
            return rt.getLeft();
        }
        rt.setRight(deleteMax(rt.getRight()));
        return rt;
    }
}
