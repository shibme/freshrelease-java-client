package me.shib.lib.freshrelease.api;

public final class IssueType {

    private String project_label;
    private Long id;
    private String name;
    private String label;
    private boolean sprintable;
    private String description;
    private boolean editable;
    private String level;
    private Integer form_id;
    private Integer workflow_id;

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

    public boolean isSprintable() {
        return sprintable;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEditable() {
        return editable;
    }

    public String getLevel() {
        return level;
    }

    public Integer getForm_id() {
        return form_id;
    }

    public Integer getWorkflow_id() {
        return workflow_id;
    }

    @Override
    public String toString() {
        return getLabel();
    }
}
