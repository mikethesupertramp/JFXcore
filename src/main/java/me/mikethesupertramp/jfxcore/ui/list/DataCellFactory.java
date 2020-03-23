package me.mikethesupertramp.jfxcore.ui.list;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import me.mikethesupertramp.jfxcore.ui.ViewContainer;
import me.mikethesupertramp.jfxcore.ui.data.DataPresenter;
import me.mikethesupertramp.jfxcore.ui.data.DataViewContainer;

import java.util.function.Consumer;

/**
 * Generic {@link ListView}'s cell factory for automatic creation of {@link ListCell}'s
 * containing {@link DataViewContainer}'s
 *
 * @param <T> Type of data
 * @param <K> Type of {@link DataViewContainer}
 */
public class DataCellFactory<T, K extends DataViewContainer<? extends DataPresenter<T>>> implements Callback<ListView<T>, ListCell<T>> {
    private Class<K> viewClass;
    private Consumer<K> modifier;

    public DataCellFactory(Class<K> viewClass) {
        this.viewClass = viewClass;
    }

    /**
     * Set a {@link Consumer} that will receive all the view instances created, in order to add custom initialization
     * code like setting listeners
     *
     * @param modifier consumer that modifies
     */
    public DataCellFactory<T, K> forEach(Consumer<K> modifier) {
        this.modifier = modifier;
        return this;
    }

    @Override
    public ListCell<T> call(ListView<T> param) {
        K view = ViewContainer.instantiate(viewClass);
        if (modifier != null) {
            modifier.accept(view);
        }
        return new DataListCell<>(view);
    }
}
