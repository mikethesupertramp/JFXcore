package me.mikethesupertramp.jfxcore.ui;

public class ViewLoadingException extends RuntimeException {
    public ViewLoadingException(String message) {
        super(message);
    }

    public ViewLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
