package me.shib.lib.freshrelease.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class IssueList {
    private List<Project> projects;
    private List<Issue> issues;
    private List<IssueType> issue_types;
    private List<Priority> priorities;
    private List<Status> statuses;
    private List<User> users;
    private Meta meta;

    public List<Issue> getIssues() {
        return issues;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<IssueType> getIssue_types() {
        return issue_types;
    }

    public List<Priority> getPriorities() {
        return priorities;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public List<User> getUsers() {
        return users;
    }

    public Meta getMeta() {
        return meta;
    }

    void backfill(FreshRelease freshRelease) {
        if (issues != null && issues.size() > 0) {
            Map<Long, Project> projectMap = new HashMap<>();
            if (projects != null) {
                for (Project project : projects) {
                    projectMap.put(project.getId(), project);
                }
            }
            for (Issue issue : this.getIssues()) {
                issue.setFreshRelease(freshRelease);
                issue.setProject(projectMap.get(issue.getProjectId()));
                issue.setIssueTypes(issue_types);
                issue.setStatuses(statuses);
                issue.setUsers(users);
                issue.setPriorities(priorities);
            }
        }
    }
}
