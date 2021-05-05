package me.shib.lib.freshrelease.api;

import java.util.Date;
import java.util.List;

public final class Activity {

    private Long id;
    private Date created_at;
    private Long user_id;
    private Action action;
    private Changes changes;

    public Long getId() {
        return id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Long getUser_id() {
        return user_id;
    }

    public Action getAction() {
        return action;
    }

    public Changes getChanges() {
        return changes;
    }

    public enum Action {
        issue_create, comment_create, issue_update
    }

    public class Change {
        private final String from;
        private final String to;

        private Change(String from, String to) {
            this.from = from;
            this.to = to;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }
    }

    public class Changes {
        private List<String> title;
        private List<String> owner;
        private List<String> status;
        private List<String> issue_type;
        private List<String> sub_project;
        private List<String> priority;
        private List<String> due_by;
        private List<String> sprint;
        private Object custom_fields;

        private Change getChangeAsObject(List<String> change) {
            if (change != null) {
                return new Change(change.get(0), change.get(1));
            }
            return null;
        }

        public Change getTitle() {
            return getChangeAsObject(title);
        }

        public Change getOwner() {
            return getChangeAsObject(owner);
        }

        public Change getStatus() {
            return getChangeAsObject(status);
        }

        public Change getIssueType() {
            return getChangeAsObject(issue_type);
        }

        public Change getSubProject() {
            return getChangeAsObject(sub_project);
        }

        public Change getPriority() {
            return getChangeAsObject(priority);
        }

        public Change getDueBy() {
            return getChangeAsObject(due_by);
        }

        public Change getSprint() {
            return getChangeAsObject(sprint);
        }

        public Object getCustomFields() {
            return custom_fields;
        }
    }

}
