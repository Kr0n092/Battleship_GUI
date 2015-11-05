import javax.swing.*;
import java.awt.*;

/**
 * Created by olivier on 11/2/15.
 */
public class Panel extends JPanel {
    private int $row;
    private int $col;

    public Panel(int row, int col) {
        setEnabled(true);
        setBackground(Color.BLUE);
        setPreferredSize(new Dimension(3,3));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.$row = row;
        this.$col = col;
    }

    public int getRow() {
        return this.$row;
    }

    public int getCol() {
        return this.$col;
    }
}
