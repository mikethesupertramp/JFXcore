package me.mikethesupertramp.jfxcore.ui.data;
import me.mikethesupertramp.jfxcore.ui.ViewContainer;

/**
 * Class that represents a data view. A view that displays single data object
 * @param <T> Type of presenter
 * @see DataPresenter
 */
public class DataViewContainer<T extends DataPresenter<?>> extends ViewContainer<T> {
    @Override
    public T getPresenter() {
        return super.getPresenter();
    }
}
