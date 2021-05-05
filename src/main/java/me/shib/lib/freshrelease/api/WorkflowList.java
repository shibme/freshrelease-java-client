package me.shib.lib.freshrelease.api;

import java.util.List;

public final class WorkflowList {

    private List<Workflow> workflows;
    private List<Transition> transitions;
    private List<Group> groups;
    private List<Status> statuses;

    public List<Workflow> getWorkflows() {
        return workflows;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public List<Status> getStatuses() {
        return statuses;
    }
}