package me.shib.lib.freshrelease.api;

public final class FreshReleaseException extends Exception {
    public FreshReleaseException(String message) {
        super(message);
    }

    public FreshReleaseException(Exception e) {
        super(e.getMessage(), e.getCause());
    }
}
