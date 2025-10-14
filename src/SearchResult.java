// -------------------------------------------------------------------------
/**
 * Class which handles necessary logic for extensive searching.
 * 
 * @author bluch
 * @version Oct 9, 2025
 */
public class SearchResult {
    // ~ Fields ................................................................
    private City[] cities;
    /**
     * Count for nodes visited.
     */
    public int count;

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................

    /**
     * Initializes necessary array.
     */
    SearchResult() {
        cities = new City[10]; // Initial capacity
        count = 0;
    }


    /**
     * Adds a city to the tree.
     * 
     * @param city
     *            city being added.
     */
    void add(City city) {
        if (count == cities.length) {
            // Resize the array if it's full
            City[] newCities = new City[cities.length * 2];
            System.arraycopy(cities, 0, newCities, 0, cities.length);
            cities = newCities;
        }
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
