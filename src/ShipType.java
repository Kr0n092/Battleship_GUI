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

    ShipType(int hits, String name) {
        this.$maxHits = hits;
        this.$name = name;
    }

    public int getMaxHits() { return this.$maxHits; }

    public String getType() { return this.$name; }



}
