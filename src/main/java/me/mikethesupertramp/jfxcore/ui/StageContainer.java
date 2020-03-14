package me.mikethesupertramp.jfxcore.ui;

import javafx.stage.Stage;
import me.mikethesupertramp.jfxcore.ui.root.RootViewContainer;

/**
 * A class representing a stage(window) and associated {@link RootViewContainer} containing a view and a presenter
 *
 * @param <T> Type of
 */
public class StageContainer<T extends RootViewContainer> {
    private final Stage stage;
    private final T content;

    public StageContainer(Stage stage, T content) {
        this.stage = stage;
        this.content = content;
    }

    /**
     * Returns a stage object
     *
     * @return stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Returns {@link RootViewContainer} associated with the stage
     *
     * @return stages content
     */
    public T getContent() {
        return content;
    }
}
