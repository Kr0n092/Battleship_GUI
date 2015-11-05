import java.util.Observable;

/**
 * Created by olivier on 11/2/15.
 */
public interface View {
    void setController(Controller controller);
    Controller getController();

    void setModel(Observable model);
    Observable getModel();

    Controller defaultController(Observable model);
}

