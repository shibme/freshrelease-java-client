package me.shib.lib.freshrelease.api;

import java.util.List;

public class ProjectList {
    private List<Project> projects;
    private Meta meta;

    public List<Project> getProjects() {
        return projects;
    }

    public Meta getMeta() {
        return meta;
    }
}