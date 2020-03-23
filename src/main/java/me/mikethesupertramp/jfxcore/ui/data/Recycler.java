package me.mikethesupertramp.jfxcore.ui.data;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import me.mikethesupertramp.jfxcore.ui.ViewContainer;

import java.util.*;
import java.util.function.Consumer;

/**
 * Class used for recycling {@link DataViewContainer} objects when there is a necessity for displaying many
 * similar items with frequently updating data.
 *
 * @param <T> Type of data
 * @param <K> Type of {@link DataViewContainer} view
 */
public class Recycler<T, K extends DataViewContainer<? extends DataPresenter<T>>> {
    private final Class<K> viewClass;
    private final Map<T, K> activeViews = new HashMap<>();
    private final List<K> views = new ArrayList<>();
    private final ObservableList<Node> container;
    private Consumer<K> modifier;

    /**
     * Main constructor
     *
     * @param view      Class of the view
     * @param container list where views should be placed
     */
    public Recycler(Class<K> view, ObservableList<Node> container) {
        this.viewClass = view;
        this.container = container;
    }

    /**
     * Set a {@link Consumer} that will receive all the view instances created, in order to add custom initialization
     * code like setting listeners
     *
     * @param modifier consumer that modifies
     */
    public void setModifier(Consumer<K> modifier) {
        this.modifier = modifier;
    }

    /**
     * Adds an element to recycler
     *
     * @param element Data element
     */
    public void addElement(T element) {
        if (!activeViews.containsKey(element)) {
            K viewContainer = allocateView();
            viewContainer.getView().setVisible(true);
            viewContainer.getPresenter().setData(element);
            activeViews.put(element, viewContainer);
        } else {
            throw new IllegalArgumentException("Element " + element + " is already present in recycler");
        }
    }

    /**
     * Checks if recycler contains certain element
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
            K viewContainer = activeViews.remove(element);
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

    private K allocateView() {
        if (activeViews.size() < views.size()) {
            return views.get(activeViews.size());
        } else {
            return createView();
        }
    }

    private K createView() {
        K viewContainer = ViewContainer.instantiate(viewClass);
        if (modifier != null) {
            modifier.accept(viewContainer);
        }
        views.add(viewContainer);
        container.add(viewContainer.getView());
        return viewContainer;
    }

}
