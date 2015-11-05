import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by olivier on 11/3/15.
 */
public class PopUp {
    private JFrame $frame;
    private JTextArea $text;
    private JButton $close;

    public PopUp(String text) {
        this.$text = new JTextArea(text);
        this.$close = new JButton("Close");
        this.$close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                $frame.dispose();
            }
        });
        $frame = new JFrame();
        $frame.setLayout(new BorderLayout());
        $frame.setPreferredSize(new Dimension(200,200));
        $frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        $frame.pack();
        $frame.setVisible(true);

        $frame.add($text, BorderLayout.CENTER);
        $frame.add($close, BorderLayout.PAGE_END);
    }
}
