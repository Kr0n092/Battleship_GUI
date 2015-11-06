import javax.swing.*;

/**
 * Created by olivier on 11/2/15.
 */
public class Battleship {
    private BattleShipView $view;
    private BattleShipModel $model;

    /**
     * Default constructor.
     * @pre TRUE
     * @post A model and a view have been initialized and view observes model
     */
    public Battleship() {
        $model = new BattleShipModel();
        $view = new BattleShipView($model, null);

        $model.addObserver($view);
    }

    /**
     * Entrypoint to the program.
     * @param args a list of arguments passed through the console, not used in this program
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Battleship game = new Battleship();
            }
        });
    }

}
