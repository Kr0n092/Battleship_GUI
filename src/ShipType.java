/**
 * Created by Olivier on 11/3/2015.
 */
public enum ShipType {
    PATROLBOAT(2, "Patrolboat"),
    DESTROYER(3, "Destroyer"),
    SUBMARINE(3, "Submarine"),
    BATTLESHIP(4, "Battleship"),
    AIRCRAFTCARRIER(5, "Aircraft carrier");

    private final int $maxHits;
    private String $name;

    /**
     * Constructor with parameters.
     * @param hits the number of hits a ship can take
     * @param name the name of the ship (also its type)
     * @pre the number of hits is an integer, at least 2 and at most 5. The name of the ship is a non-empty String
     * @post the member variables have been set with the values of the parameters
     */
    ShipType(int hits, String name) {
        this.$maxHits = hits;
        this.$name = name;
    }

    /**
     * Lets the sender of the request know the amount of hits a ship can still take.
     * @return the maximum allowed number of hits
     */
    public int getMaxHits() { return this.$maxHits; }

    /**
     * Lets the sender of the request know the type of the ship.
     * @return the type of the ship
     */
    public String getType() { return this.$name; }



}
