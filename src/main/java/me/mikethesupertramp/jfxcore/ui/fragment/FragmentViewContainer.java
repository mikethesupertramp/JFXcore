package me.mikethesupertramp.jfxcore.ui.fragment;

import me.mikethesupertramp.jfxcore.ui.ViewContainer;

/**
 * Class that represents a {@link ViewContainer} with a {@link FragmentPresenter}
 *
 * @param <T> Type of presenter
 * @see FragmentPresenter
 */
public abstract class FragmentViewContainer<T extends FragmentPresenter> extends ViewContainer {

    /**
     * Returns presenter of specified generic type
     *
     * @return presenter
     */
    @Override
    @SuppressWarnings("unchecked")
    public T getPresenter() {
        return (T) super.getPresenter();
    }
}
