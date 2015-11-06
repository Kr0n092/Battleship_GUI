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
    private ArrayList<ArrayList<Panel>> $panelList;

    /**
     * Constructor with parameters.
     * @param model the model to be observed
     * @param controller the controller through which the view will interact with the model
     * @pre the model is not null, the controller can be null
     * @post the elements of the gui are initialized and can be interacted with,
     *          the model is now observed by the view
     */
    public BattleShipView(Observable model,Controller controller) {
        super(model, controller);
        init();
    }

    /**
     * Initializes all the elements of the GUI.
     * @pre not a single elements of the gui has been initialized
     * @post all the elements are initialized
     */
    private void init() {
        createGrid();
        createInformationPanel();
        createFrame();
        addElements();
    }

    /**
     * Creates a grid of JPanels with which the user can interact.
     * @pre the grid is not initialized
     * @post the grid is initialized with a 10x10 GridLayout and all panels can be interacted with
     */
    private void createGrid() {
        $grid = new JPanel(new GridLayout(10,10));
        $grid.setMinimumSize(new Dimension(1000,900));
        $grid.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        createGridElements();
    }

    /**
     * Creates the list of panels with which the user can interact.
     * @pre the list of panels is not initialized
     * @post the list of panels is filled with Panel objects and mouse listeners are added to listen for user input
     */
    private void createGridElements() {
        $panelList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            $panelList.add(new ArrayList<>(10));
            ArrayList<Panel> row = $panelList.get(i);
            for (int j = 0; j < 10; ++j) {
                row.add(new Panel(i,j));
                createMouseListener(row.get(j));
                $grid.add(row.get(j));
            }
        }
    }

    /**
     * Adds a mouse listeners to a panel that can catch user input.
     * @param pan the panel on which the listener needs to be added
     * @pre the panel does not have a mouse listener
     * @post the panel has a mouse listener
     */
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

    /**
     * Triggers when the user has lost the game. Shows the location of the ships, disables all the cells and creates
     * the appropriate pop-up window
     */
    private void lost() {
        showShip();
        disableCells();
        createPopUp("You didn't find all my ships, I win");
    }

    /**
     * Disables all the cells of the list of panels.
     * @pre all cells are enabled
     * @post all cell are disabled
     */
    private void disableCells() {
        for (ArrayList<Panel> row: $panelList) {
            for (Panel cell: row) {
                cell.setEnabled(false);
            }
        }
    }

    /**
     * Shows the location of all the remaining ships.
     */
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

    /**
     * Creates a pop-up window that contains the given text.
     * @param text the text to be show on the window
     * @throws NullPointerException when the text is empty
     * @pre the text is not empty
     * @post a new window is shown which displays the given text
     */
    private void createPopUp(String text) throws NullPointerException {
        if (text != null) {
            new PopUp(text);
        } else {
            throw new NullPointerException("The pop-up window needs a text to be created");
        }
    }

    /**
     * Creates a panel that will display the number of turns that the user has left before he loses.
     */
    private void createInformationPanel() {
        this.$info = new JPanel(new BorderLayout());
        this.$info.setMaximumSize(new Dimension(1000,100));

    }

    /**
     * Creates the frame on which all elements will be added.
     */
    private void createFrame() {
        $frame = new JFrame("Battleship");
        $frame.setLayout(new BorderLayout());
        $frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        $frame.setPreferredSize(new Dimension(1000,1000));
        $frame.pack();
        $frame.setVisible(true);
    }

    /**
     * Adds the grid and the info panel to the frame.
     */
    private void addElements() {
        $frame.add($grid, BorderLayout.CENTER);
        $frame.add($info, BorderLayout.SOUTH);
    }

    /**
     * Creates a controller that controls the model.
     * @param model the model to be controlled
     * @throws NullPointerException when there is no model
     * @return the created controller
     * @pre the model is not null
     * @post a new controller is returned
     */
    @Override
    public Controller defaultController(Observable model) throws NullPointerException {
        if (model != null) {
            return new BattleShipController(model);
        } else {
            throw new NullPointerException("The model must not be null!!!");
        }
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
