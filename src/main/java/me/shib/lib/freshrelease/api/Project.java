package me.shib.lib.freshrelease.api;

public final class Project {

    private Long id;
    private String name;
    private String key;
    private String type;
    private Integer parent_id;
    private Integer sprint_duration;
    private Integer average_velocity;
    private String description;
    private Integer owner_id;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public Integer getSprint_duration() {
        return sprint_duration;
    }

    public Integer getAverage_velocity() {
        return average_velocity;
    }

    public String getDescription() {
        return description;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    @Override
    public String toString() {
        return getKey();
    }
}
