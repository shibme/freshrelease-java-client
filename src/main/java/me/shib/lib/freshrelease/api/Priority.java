package me.shib.lib.freshrelease.api;

public final class Priority {

    private String project_label;
    private Long id;
    private String name;
    private String label;
    private String description;
    private String color;
    private boolean editable;

    public String getProject_label() {
        return project_label;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public String getColor() {
        return color;
    }

    public boolean isEditable() {
        return editable;
    }

    @Override
    public String toString() {
        return getLabel();
    }
}
