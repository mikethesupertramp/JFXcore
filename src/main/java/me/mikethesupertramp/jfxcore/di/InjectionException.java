package me.mikethesupertramp.jfxcore.di;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class InjectionException extends RuntimeException {
    public InjectionException(Object owner, Field field) {
        super("Error injecting field \"" + field.getName() + "\" into \"" + owner.getClass()
                + "\" no instances of type \"" + field.getType() + "\" could be found");
    }

    public InjectionException(Object owner, Method method, Class type) {
        super("Error injecting method \"" + method.getName() + "\" into \"" + owner.getClass()
                + "\" no instances of type \"" + type + "\" could be found");
    }
}
