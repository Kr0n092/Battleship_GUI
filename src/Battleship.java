import javax.swing.*;

/**
 * Created by olivier on 11/2/15.
 */
public class Battleship {
    private BattleShipView $view;
    private BattleShipModel $model;

    public Battleship() {
        $model = new BattleShipModel();
        $view = new BattleShipView($model, null);

        $model.addObserver($view);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Battleship game = new Battleship();
            }
        });
    }

}
