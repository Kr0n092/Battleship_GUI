import java.util.ArrayList;

/**
 * Created by Olivier on 11/3/2015.
 */
public class Ship {
    private ShipType $type;
    private int $maxHits;
    private ArrayList<Panel> $positions;

    public Ship(ShipType type) {
        this.$type = type;
        this.$maxHits = $type.getMaxHits();
        this.$positions = new ArrayList<>($maxHits);
    }

    public int getSize() {
        return $maxHits;
    }

    public void setPosition(int row, int col) {
        $positions.add(new Panel(row, col));
    }

    public void decreaseHit() { this.$maxHits--;}

    public boolean checkSunk() {
        return this.$maxHits == 0;
    }

    public int getShipRow(int panelPosition) {
        return $positions.get(panelPosition).getRow();
    }

    public int getShipCol(int panelPosition) {
        return $positions.get(panelPosition).getCol();
    }

    public boolean checkHit(int row, int col) {
        for (Panel position : $positions) {
            if (position.getRow() == row && position.getCol() == col) {
                return true;
            }
        }
        return false;
    }

    public String getType() {
        return $type.getType();
    }

}
