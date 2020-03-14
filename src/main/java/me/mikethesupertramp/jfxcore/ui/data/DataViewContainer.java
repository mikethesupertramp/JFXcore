package me.mikethesupertramp.jfxcore.ui.data;
import me.mikethesupertramp.jfxcore.ui.ViewContainer;

/**
 * Class that represents a data view. A view that displays single data object
 * @param <T> Type of data
 * @see DataPresenter
 */
public class DataViewContainer<T> extends ViewContainer<DataPresenter<T>> {
    @Override
    public DataPresenter<T> getPresenter() {
        return super.getPresenter();
    }
}
