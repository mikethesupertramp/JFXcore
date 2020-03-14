package me.mikethesupertramp.jfxcore.ui.root;

import me.mikethesupertramp.jfxcore.ui.ViewContainer;
import me.mikethesupertramp.jfxcore.ui.fragment.FragmentViewContainer;

/**
 * Class that represents a {@link ViewContainer} directly bound to the {@link javafx.stage.Stage}
 *
 * @param <T> Type of presenter
 * @see FragmentViewContainer
 */
public abstract class RootViewContainer<T extends RootPresenter> extends FragmentViewContainer<T> {

    @Override
    public T getPresenter() {
        return super.getPresenter();
    }
}
