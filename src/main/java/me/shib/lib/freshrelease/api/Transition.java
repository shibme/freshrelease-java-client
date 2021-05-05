package me.shib.lib.freshrelease.api;

public final class Transition {
    private Long id;
    private String name;
    private Long from_status_id;
    private Long to_status_id;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getFrom_status_id() {
        return from_status_id;
    }

    public Long getTo_status_id() {
        return to_status_id;
    }

    @Override
    public String toString() {
        return getName();
    }
}
