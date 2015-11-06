import javax.swing.*;
import java.awt.*;

/**
 * Created by olivier on 11/2/15.
 */
public class Panel extends JPanel {
    private int $row;
    private int $col;

    /**
     * Constructor with parameters.
     * @param row the row on which the panel is situated
     * @param col the column on which the panel is situated
     * @pre both of the parameters are integers, at least zero but smaller than the size of the grid
     * @post the panel has a blue color, a black border and a set position on the grid
     */
    public Panel(int row, int col) {
        setEnabled(true);
        setBackground(Color.BLUE);
        setPreferredSize(new Dimension(3,3));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.$row = row;
        this.$col = col;
    }

    /**
     * Lets the sender of the request know the row on which the panel is set.
     * @return the row of the panel
     */
    public int getRow() {
        return this.$row;
    }

    /**
     * Lets the sender of the request know the column on which the panel is set.
     * @return the column of the panel
     */
    public int getCol() {
        return this.$col;
    }
}
