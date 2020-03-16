package me.mikethesupertramp.jfxcore.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import me.mikethesupertramp.jfxcore.di.Injector;

import java.io.IOException;
import java.net.URL;

/**
 * A base class for all types of view containers. Manages automatic loading of FXML and CSS files
 * and creation of a presenter
 * @param <T> Type of presenter
 */
public abstract class ViewContainer<T> {
    private static final String FXML_EXTENSION = ".fxml";
    private static final String CSS_EXTENSION = ".css";
    private static final String NAME_ENDING = "view";
    private T presenter;
    private Parent view;

    public ViewContainer() {
        load();
    }

    public T getPresenter() {
        return presenter;
    }

    public Parent getView() {
        return view;
    }


    private static Object instantiatePresenter(Class<?> clazz) {
        try {
            Object presenter = clazz.newInstance();
            Injector.performInjection(presenter);
            return presenter;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ViewLoadingException("Can not instantiate presenter " + clazz.getName(), e);
        }
    }

    private String getFileName(String extension) {
        String name = getClass().getSimpleName().toLowerCase();
        if (name.endsWith(NAME_ENDING)) {
            name = name.substring(0, name.lastIndexOf(NAME_ENDING));
        }
        return name.concat(extension);
    }

    private void load() {
        String fxmlPath = getFileName(FXML_EXTENSION);
        URL fxmlResource = getClass().getResource(fxmlPath);
        if(fxmlResource == null) {
            throw new ViewLoadingException("Could find view file " + fxmlPath);
        }

        FXMLLoader loader = new FXMLLoader(fxmlResource);
        loader.setControllerFactory(ViewContainer::instantiatePresenter);
        try {
            loader.load();
        } catch (IOException e) {
            throw new ViewLoadingException("Could find view file " + fxmlPath, e);
        }
        view = loader.getRoot();
        presenter = loader.getController();

        URL cssResource = getClass().getResource(getFileName(CSS_EXTENSION));
        if(cssResource != null) {
            view.getStylesheets().add(cssResource.toExternalForm());
        }

    }

    public static <T extends ViewContainer> T instantiate(Class<T> view) {
        try {
            return view.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ViewLoadingException("Can not instantiate view " + view.getName(), e);
        }
    }
}
