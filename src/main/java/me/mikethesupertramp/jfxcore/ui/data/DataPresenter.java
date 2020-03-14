package me.mikethesupertramp.jfxcore.ui.data;

/**
 * Interface for presenter classes, representing a data view which displays single data object
 * @param <T> Type of data displayed
 */
public interface DataPresenter<T> {
    void setData(T data);
}
