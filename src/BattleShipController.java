import java.util.Observable;

/**
 * Created by olivier on 11/2/15.
 */
public class BattleShipController extends AbstractController {

    public BattleShipController(Observable model) { super(model); }

    public boolean checkShot(int row, int col) { return ((BattleShipModel)getModel()).checkShot(row,col);}

    public void increaseTurn() {
        ((BattleShipModel)getModel()).increaseTurn();
    }


    public Ship getShip(int index) {
        return ((BattleShipModel)getModel()).getShip(index);
    }

    public int getShipsLeft() { return ((BattleShipModel)getModel()).getShipsLeft();}

    public void setHitShip(int row, int col) {
        ((BattleShipModel)getModel()).setHitShip(row, col);
    }

    public boolean checkSunk(int row, int col) {
        return ((BattleShipModel)getModel()).checkSunk(row, col);
    }

    public String getType(int row, int col) {
        return ((BattleShipModel)getModel()).getType(row, col);
    }


}
