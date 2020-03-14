package me.mikethesupertramp.jfxcore.ui.fragment;

/**
 * Interface for presenter classes, provides most common methods for simpler generification of presenters
 */
public interface FragmentPresenter {
    /**
     * A method called when a view associated with this presenter is being shown
     */
    default void onShow() {
    }

    /**
     * A method called when a view associated with this presenter is hidden
     */
    default void onHide() {
    }

    /**
     * A method called when a view associated with this presenter should halt performing any further operations
     */
    default void onPause() {
    }

    /**
     * A method called when a view associated with this presenter should continue normal operation after being paused
     */
    default void onResume() {
    }
}
