package me.mikethesupertramp.jfxcore.ui.root;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Interface for presenter associated with view bound directly to stage
 */
public interface RootPresenter {
    /**
     * A method called when a {@link Stage} associated with this presenter is created in order to set up the stage
     */
    default void onStageCreated(Stage stage) {
    }

    default void onStageShowing(WindowEvent e) {

    }

    default void onStageHiding(WindowEvent e) {

    }
}
