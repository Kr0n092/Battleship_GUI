import java.util.ArrayList;

/**
 * Created by Olivier on 11/3/2015.
 */
public class Ship {
    private ShipType $type;
    private int $maxHits;
    private ArrayList<Panel> $positions;

    /**
     * Constructor with parameters.
     * @param type the type of the ship
     * @pre the type is an enum and is not null
     * @post the member variables of the ship have been set
     */
    public Ship(ShipType type) {
        this.$type = type;
        this.$maxHits = $type.getMaxHits();
        this.$positions = new ArrayList<>($maxHits);
    }

    /**
     * Returns the (not hit) size of the ship.
     * @return the number of hits it can take
     */
    public int getSize() {
        return $maxHits;
    }

    /**
     * Adds a new panel to position of the ship.
     * @param row the row on which a part of the ship will be set
     * @param col the column on which a part of the ship will be set
     * @pre the row and column parameters are integers,
     *                   at least zero but never larger than the size of the grid
     */
    public void setPosition(int row, int col) {
        $positions.add(new Panel(row, col));
    }

    /**
     * Decreases the number of hits a ship can take.
     */
    public void decreaseHit() { this.$maxHits--;}

    /**
     * Checks if the ship can take another hit.
     * @return true if the ship cannot take another hit, false if it can
     */
    public boolean checkSunk() {
        return this.$maxHits == 0;
    }

    /**
     * Returns the row on which a part of the ship is located.
     * @param panelPosition the place of a panel in the ship's list of panels it is situated
     * @return the row of the panel
     * @pre the position of the panel is an integer, at least zero but smaller than the size of the panellist
     * @post a row is returned
     */
    public int getShipRow(int panelPosition) {
        return $positions.get(panelPosition).getRow();
    }

    /**
     * Returns the column on which a part of the ship is located.
     * @param panelPosition the place of a panel in the ship's list of panels it is situated
     * @return the column of the panel
     * @pre the position of the panel is an integer, at least zero but smaller than the size of the panellist
     * @post a column is returned
     */
    public int getShipCol(int panelPosition) {
        return $positions.get(panelPosition).getCol();
    }

    /**
     * Checks if a given row and column appear in a panel on which part of the ship is located.
     * @param row the row to be checked
     * @param col the column to be checked
     * @return true if the row and column match a panel, false if not
     * @pre the row and column are integers, at least zero and smaller than the size of the grid
     * @post the function returns true or false
     */
    public boolean checkHit(int row, int col) {
        for (Panel position : $positions) {
            if (position.getRow() == row && position.getCol() == col) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the type of the ship.
     * @return the type of the ship
     * @pre the ship has a type
     * @post the type of the ship is returned
     */
    public String getType() {
        return $type.getType();
    }

}
