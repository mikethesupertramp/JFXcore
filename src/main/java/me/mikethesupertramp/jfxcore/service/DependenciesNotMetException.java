package me.mikethesupertramp.jfxcore.service;

public class DependenciesNotMetException extends RuntimeException {
    public DependenciesNotMetException(String message) {
        super(message);
    }
}
