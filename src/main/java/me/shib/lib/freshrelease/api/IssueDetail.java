package me.shib.lib.freshrelease.api;

import java.util.List;

class IssueDetail {

    private Issue issue;
    private List<IssueType> issue_types;
    private List<Status> statuses;
    private List<Priority> priorities;
    private List<User> users;
    private List<Transition> transitions;

    Issue getIssue() {
        return issue;
    }

    List<IssueType> getIssue_types() {
        return issue_types;
    }

    List<Status> getStatuses() {
        return statuses;
    }

    List<Priority> getPriorities() {
        return priorities;
    }

    List<User> getUsers() {
        return users;
    }

    List<Transition> getTransitions() {
        return transitions;
    }


}
