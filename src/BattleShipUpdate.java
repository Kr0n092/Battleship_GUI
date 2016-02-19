/**
 * Created by olivier on 11/2/15.
 */
public class BattleShipUpdate {
    private int $turnsLeft;

    /**
     * Constructor with parameters.
     * @param turns the amount of turns a user has left
     * @pre the parameters are integers, both at least 0
     * @post the member variables are set with the value of the parameters
     */
    public BattleShipUpdate(int turns) {
        this.$turnsLeft = turns;
    }

    /**
     * Lets the sender of the request know the amount of turns a user can make
     * @return the number of turns left
     */
    public int getTurnsLeft() { return this.$turnsLeft; }

}
