package me.mikethesupertramp.jfxcore.ui;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.mikethesupertramp.jfxcore.di.Injector;
import me.mikethesupertramp.jfxcore.service.Service;
import me.mikethesupertramp.jfxcore.service.ServiceManager;
import me.mikethesupertramp.jfxcore.ui.root.RootViewContainer;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for managing Stages, Views and presenters.
 */
public class UserInterface implements Service {
    private final Map<Class<? extends RootViewContainer>, StageContainer> stages = new HashMap<>();
    private final List<Class<? extends RootViewContainer>> viewsToInit = new ArrayList<>();
    private URL rootCss;
    private ServiceManager serviceManager;

    @Override
    public void init(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
        Injector.addInjectSource(this::instanceSupplier);
    }

    @Override
    public void postInit(ServiceManager serviceManager) {
        Platform.runLater(() -> viewsToInit.forEach(clazz -> {
            try {
                RootViewContainer viewInstance = clazz.newInstance();
                createStage(viewInstance);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }));
    }

    /**
     * Creates stage for a given {@link RootViewContainer} and wires up necessary events
     *
     * @param viewContainer a View to be placed inside the stage
     */
    public void createStage(RootViewContainer viewContainer) {
        Stage stage = new Stage();
        StageContainer<?> container = new StageContainer<>(stage, viewContainer);
        stages.put(viewContainer.getClass(), container);

        Scene scene = new Scene(viewContainer.getView());
        if(rootCss != null) {
            scene.getStylesheets().add(rootCss.toExternalForm());
        }
        stage.setScene(scene);

        stage.setOnShowing(e -> viewContainer.getPresenter().onShow());
        stage.setOnHiding(e -> viewContainer.getPresenter().onHide());

        viewContainer.getPresenter().onStageCreated(stage);
    }

    /**
     * Returns {@link RootViewContainer} of given class if available
     *
     * @param view Class of required view
     * @param <T>  Type of view container
     * @return ViewContainer
     * @throws IllegalArgumentException if view can not be found
     */
    public <T extends RootViewContainer> T getView(Class<T> view) {
        return getStageContainer(view).getContent();
    }

    /**
     * Returns a stage containing a given view
     *
     * @param view Class of the {@link RootViewContainer}
     * @return Stage containing a given view
     */
    public Stage getStage(Class<? extends RootViewContainer> view) {
        return getStageContainer(view).getStage();
    }

    /**
     * Register a class of view to be initialized and added to separate stage. A method must be called before
     * initialization
     *
     * @param clazz Class of {@link RootViewContainer} to be initialized
     */
    public void registerView(Class<? extends RootViewContainer> clazz) {
        viewsToInit.add(clazz);
    }

    /**
     * Sets the location of a root css file that will be added to all the stages in {@link UserInterface}
     * Usually used for defining colors and application wide style classes
     * @param rootCss URL of a css file
     */
    public void setRootCss(URL rootCss) {
        this.rootCss = rootCss;
    }

    @SuppressWarnings("unchecked")
    private <T extends RootViewContainer> StageContainer<T> getStageContainer(Class<T> view) {
        if (stages.containsKey(view)) {
            return stages.get(view);
        } else {
            throw new IllegalArgumentException("View of class " + view.getName() + " does not exist");
        }
    }


    @SuppressWarnings({"unchecked", "SuspiciousMethodCalls"})
    private Object instanceSupplier(Class<?> key) {
        if (stages.containsValue(key)) {
            return stages.get(key);
        } else {
            for (StageContainer stage : stages.values()) {
                ViewContainer view = stage.getContent();
                if (key.isInstance(view)) {
                    return view;
                } else if (key.isInstance(view.getPresenter())) {
                    return view.getPresenter();
                }
            }
        }
        return null;
    }

    @Override
    public void shutdown(ServiceManager serviceManager) {
        stages.values().forEach(i -> i.getStage().close());
    }



}
