import java.util.Observable;

/**
 * Created by olivier on 11/2/15.
 */
public abstract class AbstractController implements Controller{
    private View $view;
    private Observable $model;

    public AbstractController(Observable model) {
        setModel(model);
    }

    @Override
    public void setView(View view) {
        $view = view;
    }

    @Override
    public View getView() {
        return $view;
    }

    @Override
    public void setModel(Observable model) {
        $model = model;
    }

    @Override
    public Observable getModel() {
        return $model;
    }
}
