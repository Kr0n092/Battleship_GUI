import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by olivier on 11/2/15.
 */
public class BattleShipView extends AbstractView {
    private JPanel $grid;
    private JFrame $frame;
    private JPanel $info;
    private int $rows;
    private int $cols;
    private ArrayList<ArrayList<Panel>> $panelList;

    public BattleShipView(Observable model,Controller controller) {
        super(model, controller);
        init();
    }

    private void init() {
        createGrid();
        createInformationPanel();
        createFrame();
        addElements();
    }

    private void createGrid() {
        $grid = new JPanel(new GridLayout(10,10));
        $grid.setMinimumSize(new Dimension(1000,900));
        $grid.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        createGridElements();
    }

    private void createGridElements() {
        this.$rows = 10;
        this.$cols = 10;
        $panelList = new ArrayList<>($rows);
        for (int i = 0; i < $rows; i++) {
            $panelList.add(new ArrayList<>($cols));
            ArrayList<Panel> row = $panelList.get(i);
            for (int j = 0; j < $cols; ++j) {
                row.add(new Panel(i,j));
                createMouseListener(row.get(j));
                $grid.add(row.get(j));
            }
        }
    }

    private void createMouseListener(Panel pan) {
        pan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                Panel clickedBox = (Panel) mouseEvent.getSource();
                int clickedRow = clickedBox.getRow();
                int clickedCol = clickedBox.getCol();
                boolean hit = ((BattleShipController) getController()).
                        checkShot(clickedRow, clickedCol);
                if (clickedBox.getBackground() == Color.BLUE && hit) {
                    clickedBox.setBackground(Color.RED);
                    ((BattleShipController)getController()).setHitShip(clickedRow, clickedCol);
                    ((BattleShipController) getController()).increaseTurn();

                    if (((BattleShipController)getController()).checkSunk(clickedRow, clickedCol)) {
                        createPopUp("You sunk my " + ((BattleShipController)getController()).getType(clickedRow, clickedCol) + "!");
                    }
                } else if (!hit) {
                    clickedBox.setBackground(Color.GRAY);
                    ((BattleShipController) getController()).increaseTurn();
                }


            }
        });
    }

    private void lost() {
        showShip();
        disableCells();
        createPopUp("You didn't find all my ships, I win");
    }

    private void disableCells() {
        for (ArrayList<Panel> row: $panelList) {
            for (Panel cell: row) {
                cell.setEnabled(false);
            }
        }
    }

    private void showShip() {
        int numberOfShips = ((BattleShipController)getController()).getShipsLeft();
        int i = 0;
        while (i < numberOfShips) {
            Ship ship = ((BattleShipController)getController()).getShip(i);
            for (int j = 0; j < ship.getSize(); ++j) {
                int shipRow = ship.getShipRow(j);
                int shipCol = ship.getShipCol(j);
                $panelList.get(shipRow).get(shipCol).setBackground(Color.RED);
            }
            ++i;

        }
        disableCells();
    }

    private void createPopUp(String text) {
        new PopUp(text);
    }

    private void createInformationPanel() {
        this.$info = new JPanel(new BorderLayout());
        this.$info.setMaximumSize(new Dimension(1000,100));

    }

    private void createFrame() {
        $frame = new JFrame("Battleship");
        $frame.setLayout(new BorderLayout());
        $frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        $frame.setPreferredSize(new Dimension(1000,1000));
        $frame.pack();
        $frame.setVisible(true);
    }

    private void addElements() {
        $frame.add($grid, BorderLayout.CENTER);
        $frame.add($info, BorderLayout.SOUTH);
    }

    @Override
    public Controller defaultController(Observable model) {
        return new BattleShipController(model);
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        BattleShipUpdate info = (BattleShipUpdate) arg;
        int turnsLeft = info.getTurnsLeft();
        $info.removeAll();
        $info.add(new JTextField("Number of turns left: " + turnsLeft));
        $frame.revalidate();
        if (turnsLeft == 0) {
            lost();
        }
    }
}
