package me.mikethesupertramp.jfxcore.ui.root;

import me.mikethesupertramp.jfxcore.ui.ViewContainer;

/**
 * Class that represents a {@link ViewContainer} directly bound to the {@link javafx.stage.Stage}
 *
 * @param <T> Type of presenter
 */
public abstract class RootViewContainer<T extends RootPresenter> extends ViewContainer<T> {

    @Override
    public T getPresenter() {
        return super.getPresenter();
    }
}
