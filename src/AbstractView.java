import java.util.Observable;
import java.util.Observer;

/**
 * Created by olivier on 11/2/15.
 */
public abstract class AbstractView implements View, Observer{
    private Observable $model;
    private Controller $controller;

    public AbstractView(Observable model, Controller controller) {
        setModel(model);
        setController(controller);
    }
    @Override
    public void setController(Controller controller) {
        $controller = controller;

        getController().setView(this);
    }

    @Override
    public Controller getController() {
        if (this.$controller == null) {
            setController(defaultController(getModel()));
        }
        return $controller;
    }

    @Override
    public void setModel(Observable model) {
        $model = model;
    }

    @Override
    public Observable getModel() {
        return $model;
    }

    @Override
    public Controller defaultController(Observable model) {
        return null;
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

    }
}
