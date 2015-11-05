/**
 * Created by olivier on 11/2/15.
 */
public class BattleShipUpdate {
    private int $turnsLeft;

    public BattleShipUpdate(int left) {
        this.$turnsLeft = left;
    }

    public int getTurnsLeft() { return this.$turnsLeft; }
}
