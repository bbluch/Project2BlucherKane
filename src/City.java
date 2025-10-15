// -------------------------------------------------------------------------
/**
 * Contains the necessary info and implementation for each city.
 * Each city has a name as well as X and Y coordinates. City names may be
 * the same, but two cities cannot have the same coordinates.
 * 
 * @author benblucher, austink23
 * @version Oct 8, 2025
 */
public class City {
    // ~ Fields ................................................................
    private String name;
    private int x;
    private int y;
    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................

    /**
     * Constructs a new City object.
     *
     * @param name
     *            The name of the city.
     * @param x
     *            The x-coordinate of the city.
     * @param y
     *            The y-coordinate of the city.
     */
    public City(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }


    /**
     * Gets the name of the city.
     *
     * @return The city's name.
     */
    public String getName() {
        return name;
    }


    /**
     * Gets the x-coordinate of the city.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }


    /**
     * Gets the y-coordinate of the city.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }


    /**
     * Returns a string representation of the city.
     *
     * @return A string in the format "CityName (x, y)".
     */
    @Override
    public String toString() {
        return name + " (" + x + ", " + y + ")";
    }
}
