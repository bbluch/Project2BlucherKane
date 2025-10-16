// -------------------------------------------------------------------------
/**
 * Class which handles necessary logic for extensive searching. This
 * mainly handles the task of storing the nodes visited information when
 * it is needed.
 * 
 * @author benblucher, austink23
 * @version Oct 9, 2025
 */
public class SearchResult {
    // ~ Fields ................................................................
    private City[] cities;
    private int count;
    /**
     * Count for Nodes Visited.
     */
    public int nodesVisited;

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................

    /**
     * Initializes necessary array.
     */
    SearchResult() {
        cities = new City[2]; // Initial capacity
        count = 0;
        nodesVisited = 0;
    }


    /**
     * Adds a city to the tree.
     * 
     * @param city
     *            city being added.
     */
    void add(City city) {
        // Resize the array if it's full
        City[] newCities = new City[cities.length + 1];
        System.arraycopy(cities, 0, newCities, 0, cities.length);
        cities = newCities;
        
        //add the city
        cities[count++] = city;
    }


    /**
     * Gets the array after operations.
     * 
     * @return the array needed.
     */
    City[] getResults() {
        // Trim the array to the actual size
        City[] trimmedCities = new City[count];
        System.arraycopy(cities, 0, trimmedCities, 0, count);
        return trimmedCities;
    }
}
