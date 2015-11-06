/**
 * Created by olivier on 11/2/15.
 */
public class BattleShipUpdate {
    private int $turnsLeft;

    /**
     * Constructor with parameters.
     * @param left the amount of turns a user has left
     * @pre the parameter is an integer, at least 0
     * @post the member variable is set with the value of the parameter
     */
    public BattleShipUpdate(int left) {
        this.$turnsLeft = left;
    }

    /**
     * Lets the sender of the request know the amount of turns a user can make
     * @return the number of turns left
     */
    public int getTurnsLeft() { return this.$turnsLeft; }
}
