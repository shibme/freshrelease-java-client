package me.shib.lib.freshrelease.api;

import java.util.List;

public final class WorkflowInfo {

    private Workflow workflow;
    private List<Transition> transitions;
    private List<Group> groups;
    private List<Status> statuses;

    public Workflow getWorkflow() {
        return workflow;
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
