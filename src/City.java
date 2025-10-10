// -------------------------------------------------------------------------
/**
 * Contains the necessary info and implementation for each city.
 * 
 * @author benblucher, austink23
 * @version Oct 8, 2025
 */
public class City {
    // ~ Fields ................................................................

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................
    private String name;
    private int x;
    private int y;

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

// /**
// * Checks if this city is equal to another object.
// *
// * @param obj
// * The object to compare with.
// * @return True if the objects are equal, false otherwise.
// */
// @Override
// public boolean equals(Object obj) {
// if (this == obj) {
// return true;
// }
// if (obj == null || getClass() != obj.getClass()) {
// return false;
// }
// City city = (City)obj;
// return x == city.x && y == city.y && name.equals(city.name);
// }
}
