package me.mikethesupertramp.jfxcore.service;


import me.mikethesupertramp.jfxcore.di.Injector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * A class that handles initialization, dependency management, access and disposal of services
 */
public class ServiceManager {
    private final Queue<Service> uninitializedServices = new LinkedList<>();
    private final List<Service> initializedServices = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public ServiceManager() {
        Injector.addInjectSource(clazz -> {
            if (Service.class.isAssignableFrom(clazz)) {
                return getService((Class<Service>) clazz);
            } else {
                return null;
            }
        });
    }

    /**
     * Adds service to the manager
     *
     * @param service a Service
     */
    public void addService(Service service) {
        uninitializedServices.offer(service);
    }

    /**
     * Initializes all the services currently present in ServiceManager.
     *
     * @throws DependenciesNotMetException if dependencies of any of the services can not be satisfied
     */
    public void init() {
        while (uninitializedServices.peek() != null) {
            Service service = uninitializedServices.poll();
            boolean dependenciesMet = true;
            for (Class<? extends Service> dependency : service.getDependencies()) {
                if (initializedServices.stream().noneMatch(dependency::isInstance)) {
                    if (uninitializedServices.stream().anyMatch(dependency::isInstance)) {
                        //Dependency has not been initialized yet
                        dependenciesMet = false;
                        break;
                    } else {
                        //Dependency is nor initialized nor added to the queue, throw an exception
                        throw new DependenciesNotMetException("Dependency " + dependency.getName()
                                + " of service " + service.getClass().getName() + " could not be satisfied");
                    }
                }
            }

            if (dependenciesMet) {
                service.init(this);
                initializedServices.add(service);
            } else {
                //Dependencies have not been met yet, put current service to the end of a queue
                uninitializedServices.offer(service);
            }
        }

        //Post init all the services
        initializedServices.forEach(s -> s.postInit(this));
    }

    /**
     * Returns an instance of the specified service class
     *
     * @param clazz Class or parent of the required service
     * @return Service of correct type if present otherwise null
     */
    @SuppressWarnings("unchecked")
    public <T extends Service> T getService(Class<T> clazz) {
        return (T) initializedServices.stream().filter(clazz::isInstance).findAny().orElse(null);
    }

    /**
     * Shuts down all the services
     */
    public void shutDown() {
        initializedServices.forEach(s -> s.shutdown(this));
    }

}
