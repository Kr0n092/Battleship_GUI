import java.util.Observable;

/**
 * Created by olivier on 11/2/15.
 */
public interface Controller {
    void setView(View view);
    View getView();
    void setModel(Observable model);
    Observable getModel();
}
