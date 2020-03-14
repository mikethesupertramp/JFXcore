package me.mikethesupertramp.jfxcore.ui.root;

import javafx.stage.Stage;
import me.mikethesupertramp.jfxcore.ui.fragment.FragmentPresenter;

/**
 * Advanced version of {@link FragmentPresenter} interface for root views bound directly to stages
 */
public interface RootPresenter extends FragmentPresenter {
    /**
     * A method called when a {@link Stage} associated with this presenter is created in order to set up the stage
     */
    default void onStageCreated(Stage stage) {
    }
}
