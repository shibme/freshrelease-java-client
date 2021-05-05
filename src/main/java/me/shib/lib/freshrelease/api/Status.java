package me.shib.lib.freshrelease.api;

public final class Status {

    private String project_label;
    private Long id;
    private String name;
    private String label;
    private String description;
    private Integer status_category_id;
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

    public Integer getStatus_category_id() {
        return status_category_id;
    }

    public boolean isEditable() {
        return editable;
    }

    @Override
    public String toString() {
        return getLabel();
    }
}
