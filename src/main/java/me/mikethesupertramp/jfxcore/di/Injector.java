package me.mikethesupertramp.jfxcore.di;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

//TODO add javadocs
public class Injector {
    private static List<Function<Class, Object>> injectSources = new ArrayList<>();
    public static void performInjection(Object object) {
        Class clazz = object.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Inject.class)) {
                    injectField(object, field);
                }
            }
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Inject.class)) {
                    injectMethod(object, method);
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    public static void addInjectSource(Function<Class, Object> injectSource) {
        injectSources.add(injectSource);
    }

    public static void resetInjectionSources() {
        injectSources.clear();
    }

    private static void injectField(Object owner, Field field) {
        Class<?> fieldClass = field.getType();
        Object instance = findInstance(fieldClass);

        if(instance != null) {
            field.setAccessible(true);
            try {
                field.set(owner, instance);
            } catch (IllegalAccessException e) {
                e.printStackTrace(); //Should not happen
            }
        } else {
            throw new InjectionException(owner, field);
        }
    }

    private static void injectMethod(Object owner, Method method) {
        Class[] argTypes = method.getParameterTypes();
        Object[] args = new Object[argTypes.length];

        for(int i=0; i<argTypes.length; i++) {
            Object instance = findInstance(argTypes[i]);

            if (instance != null) {
                args[i] = instance;
            } else {
                throw new InjectionException(owner, method, argTypes[i]);
            }
        }

        method.setAccessible(true);

        try {
            method.invoke(owner, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace(); //Should not happen
        }
    }

    private static Object findInstance(Class clazz) {
        for (Function<Class, Object> injectSource : injectSources) {
            Object result = injectSource.apply(clazz);
            if(result != null) {
                return result;
            }
        }
        return null;
    }
}
