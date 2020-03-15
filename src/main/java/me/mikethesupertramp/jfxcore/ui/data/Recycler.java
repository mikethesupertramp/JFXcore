package me.mikethesupertramp.jfxcore.ui.data;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import me.mikethesupertramp.jfxcore.ui.ViewContainer;

import java.util.*;

/**
 * Class used for recycling {@link DataViewContainer} objects when there is a necessity display many
 * similar data view with frequently updating data.
 *
 * @param <T>
 */
public class Recycler<T> {
    private final Class<? extends DataViewContainer<T>> viewClass;
    private final Map<T, DataViewContainer<T>> activeViews = new HashMap<>();
    private final List<DataViewContainer<T>> views = new LinkedList<>();
    private final ObservableList<Node> container;

    /**
     * Main constructor
     *
     * @param view      Class of the view
     * @param container list where views should be placed
     */
    public Recycler(Class<? extends DataViewContainer<T>> view, ObservableList<Node> container) {
        this.viewClass = view;
        this.container = container;
    }

    /**
     * Adds an element to recycler
     *
     * @param element Data element
     */
    public void addElement(T element) {
        if (!activeViews.containsKey(element)) {
            DataViewContainer<T> viewContainer = allocateView();
            viewContainer.getView().setVisible(true);
            viewContainer.getPresenter().setData(element);
            activeViews.put(element, viewContainer);
        } else {
            throw new IllegalArgumentException("Element " + element + " is already present in recycler");
        }
    }

    /**
     * Checks weather recycler contains certain element
     *
     * @param element Data element
     * @return true if recycler contains element
     */
    public boolean containsElement(T element) {
        return activeViews.containsKey(element);
    }

    /**
     * Removes all specified elements
     *
     * @param elements Collection of elements to be removed
     */
    public void removeAll(Collection<T> elements) {
        elements.forEach(this::removeElement);
    }

    /**
     * Adds all specified elements
     *
     * @param elements Collection of elements to be added
     */
    public void addAll(Collection<T> elements) {
        elements.forEach(this::addElement);
    }

    /**
     * Clears recycler by removing all elements
     */
    public void clear() {
        removeAll(new HashSet<>(activeViews.keySet()));
    }

    /**
     * Removes specified element
     *
     * @param element Element to be removed
     */
    public void removeElement(T element) {
        if (activeViews.containsKey(element)) {
            DataViewContainer<T> viewContainer = activeViews.remove(element);
            viewContainer.getView().setVisible(false);
        }
    }

    /**
     * Sets all elements in recycler to the specified
     *
     * @param elements New elements
     */
    public void setAll(Collection<T> elements) {
        clear();
        addAll(elements);
    }

    private DataViewContainer<T> allocateView() {
        if (activeViews.size() < views.size()) {
            return views.get(activeViews.size());
        } else {
            return createView();
        }
    }

    private DataViewContainer<T> createView() {
        DataViewContainer<T> viewContainer = ViewContainer.instantiate(viewClass);
        views.add(viewContainer);
        container.add(viewContainer.getView());
        return viewContainer;
    }

}
