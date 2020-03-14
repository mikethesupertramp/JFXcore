package me.mikethesupertramp.jfxcore.ui.list;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import me.mikethesupertramp.jfxcore.ui.ViewLoadingException;
import me.mikethesupertramp.jfxcore.ui.data.DataViewContainer;

/**
 * Utility class for automatic creation of {@link ListView}'s CellFactories with {@link DataListCell}'s
 *
 * @see DataListCell
 */
public class ListViewUtil {
    /**
     * Create a cellFactory for a {@link ListView}
     *
     * @param view Class of {@link DataViewContainer} to be placed inside cells
     * @param <T>  Type of data
     * @return {@link ListView}'s cell factory
     */
    public static <T> Callback<ListView<T>, ListCell<T>> factoryFor(Class<? extends DataViewContainer<T>> view) {
        return (p) -> createCell(view);
    }

    private static <T> ListCell<T> createCell(Class<? extends DataViewContainer<T>> view) {
        try {
            return new DataListCell<>(view.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ViewLoadingException("Can not instantiate view " + view.getName(), e);
        }
    }
}
