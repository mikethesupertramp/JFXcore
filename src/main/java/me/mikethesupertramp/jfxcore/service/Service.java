package me.mikethesupertramp.jfxcore.service;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface exposing basic functions of a service
 */
public interface Service {
    /**
     * A method called from a service manager whenever all service dependencies have been met to initialize
     * current service
     *
     * @param serviceManager ServiceManager instance for retrieving dependencies
     */
    default void init(ServiceManager serviceManager) {
    }

    /**
     * A method called from the ServiceManager after all of the services have been initialized
     *
     * @param serviceManager ServiceManager instance for retrieving dependencies
     */
    default void postInit(ServiceManager serviceManager) {
    }

    /**
     * A method called from the ServiceManager in order to perform any shutdown operations if required
     *
     * @param serviceManager ServiceManager instance for retrieving dependencies
     */
    default void shutdown(ServiceManager serviceManager) {
    }

    /**
     * @return List of classes of dependencies that need to be initialized before this service, returns an empty
     * list by default
     */
    default List<Class<? extends Service>> getDependencies() {
        return new ArrayList<>();
    }
}
