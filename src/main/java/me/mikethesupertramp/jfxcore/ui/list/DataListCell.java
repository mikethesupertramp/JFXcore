package me.mikethesupertramp.jfxcore.ui.list;

import javafx.scene.control.ListCell;
import me.mikethesupertramp.jfxcore.ui.data.DataPresenter;
import me.mikethesupertramp.jfxcore.ui.data.DataViewContainer;

/**
 * Class that represents a generic ListCell containing a {@link DataViewContainer}
 * @param <T> Type of data object
 */
public class DataListCell<T> extends ListCell<T> {
    private DataViewContainer<DataPresenter<T>> viewContainer;

    public DataListCell(DataViewContainer<DataPresenter<T>> viewContainer) {
        this.viewContainer = viewContainer;
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        if (empty || item == null) {
            setGraphic(null);
        } else {
            viewContainer.getPresenter().setData(item);
            setGraphic(viewContainer.getView());
        }
    }
}
