package me.shib.lib.freshrelease.api;

import java.util.*;

public final class Issue {

    private Long id;
    private String key;
    private String title;
    private String name;
    private Long issue_type_id;
    private Long status_id;
    private Long priority_id;
    private Long owner_id;
    private Long project_id;
    private Long parent_id;
    private Long sub_project_id;
    private Date start_date;
    private Date due_by;
    private Long position;
    private Long sprint_id;
    private Long release_id;
    private Long story_points;
    private Long reporter_id;
    private Date created_at;
    private Integer eta_flag;
    private String description;
    private Long epic_id;
    private List<String> tags;
    private Map<String, String> links;
    private boolean resolved;
    private Date status_changed_at;
    private Date updated_at;
    private Date resolved_at;
    private Date started_at;
    private boolean blocked;
    private Date blocked_at;
    private boolean deleted;
    private List<Long> transition_ids;
    private Map<String, Object> custom_field;

    private transient FreshRelease freshRelease;
    private transient String projectKey;
    private transient Project project;
    private transient String url;
    private transient Map<Long, IssueType> issueTypeMap;
    private transient Map<Long, Priority> priorityMap;
    private transient Map<Long, User> userMap;
    private transient Map<Long, Status> statusMap;
    private transient Map<Long, Transition> transitionMap;
    private transient List<Comment> comments;
    private transient List<Activity> activities;

    public Issue(String title, Long issue_type_id) {
        this.title = title;
        this.issue_type_id = issue_type_id;
    }

    void setFreshRelease(FreshRelease freshRelease) {
        this.freshRelease = freshRelease;
    }

    public String getUrl() {
        if (url == null) {
            url = freshRelease.getUrl() + "ws/" + getProjectKey() + "/tasks/" + key;
        }
        return url;
    }

    private void populateIssueTypes() throws FreshReleaseException {
        List<IssueType> issueTypes = freshRelease.getIssueTypes();
        issueTypeMap = new HashMap<>();
        for (IssueType issueType : issueTypes) {
            issueTypeMap.put(issueType.getId(), issueType);
        }
    }

    public synchronized IssueType getIssueType(Long issueTypeId) {
        if (issueTypeMap == null) {
            try {
                populateIssueTypes();
            } catch (FreshReleaseException e) {
                e.printStackTrace();
            }
        }
        return issueTypeMap.get(issueTypeId);
    }

    public IssueType getIssueType() {
        return getIssueType(issue_type_id);
    }

    void setIssueTypes(Collection<IssueType> issueTypes) {
        if (issueTypes != null) {
            if (issueTypeMap == null) {
                issueTypeMap = new HashMap<>();
            }
            for (IssueType issueType : issueTypes) {
                issueTypeMap.put(issueType.getId(), issueType);
            }
        }
    }

    private void populatePriorities() throws FreshReleaseException {
        List<Priority> priorities = freshRelease.getPriorities();
        priorityMap = new HashMap<>();
        for (Priority priority : priorities) {
            priorityMap.put(priority.getId(), priority);
        }
    }

    public synchronized Priority getPriority(Long priorityId) {
        if (priorityMap == null) {
            try {
                populatePriorities();
            } catch (FreshReleaseException e) {
                e.printStackTrace();
            }
        }
        return priorityMap.get(priorityId);
    }

    public Priority getPriority() {
        return getPriority(priority_id);
    }

    void setPriorities(Collection<Priority> priorities) {
        if (priorities != null) {
            if (priorityMap == null) {
                priorityMap = new HashMap<>();
            }
            for (Priority priority : priorities) {
                priorityMap.put(priority.getId(), priority);
            }
        }
    }

    private void populateUsers() throws FreshReleaseException {
        List<User> users = freshRelease.getUsers();
        userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getId(), user);
        }
    }

    public synchronized User getUser(Long userId) {
        if (userMap == null) {
            try {
                populateUsers();
            } catch (FreshReleaseException e) {
                e.printStackTrace();
            }
        }
        return userMap.get(userId);
    }

    public User getOwner() {
        return getUser(owner_id);
    }

    public User getReporter() {
        return getUser(reporter_id);
    }

    void setUsers(Collection<User> users) {
        if (users != null) {
            if (userMap == null) {
                userMap = new HashMap<>();
            }
            for (User user : users) {
                userMap.put(user.getId(), user);
            }
        }
    }

    private void populateStatuses() throws FreshReleaseException {
        List<Status> statuses = freshRelease.getStatuses();
        statusMap = new HashMap<>();
        for (Status status : statuses) {
            statusMap.put(status.getId(), status);
        }
    }

    public synchronized Status getStatus(Long statusId) {
        if (statusMap == null) {
            try {
                populateStatuses();
            } catch (FreshReleaseException e) {
                e.printStackTrace();
            }
        }
        return statusMap.get(statusId);
    }

    public Status getStatus() {
        return getStatus(status_id);
    }

    void setStatuses(Collection<Status> statuses) {
        if (statuses != null) {
            if (statusMap == null) {
                statusMap = new HashMap<>();
            }
            for (Status status : statuses) {
                statusMap.put(status.getId(), status);
            }
        }
    }

    public Transition getTransition(Long transitionId) {
        if (transitionMap != null) {
            return transitionMap.get(transitionId);
        }
        return null;
    }

    public List<Transition> getTransitions() {
        if (transitionMap != null) {
            return new ArrayList<>(transitionMap.values());
        }
        return null;
    }

    void setTransitions(Collection<Transition> transitions) {
        if (transitionMap == null) {
            transitionMap = new HashMap<>();
        }
        for (Transition transition : transitions) {
            transitionMap.put(transition.getId(), transition);
        }
    }

    public Project getProject() {
        return this.project;
    }

    void setProject(Project project) {
        this.project = project;
        this.projectKey = project.getKey();
    }

    public Long getProjectId() {
        return project_id;
    }

    public Date getStartDate() {
        return start_date;
    }

    public Long getPosition() {
        return position;
    }

    public Date getCreateAt() {
        return created_at;
    }

    public Integer getEtaFlag() {
        return eta_flag;
    }

    public Long getEpicId() {
        return epic_id;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public boolean isResolved() {
        return resolved;
    }

    public Date getStatusChangedAt() {
        return status_changed_at;
    }

    public Date getStartedAt() {
        return started_at;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public Date getBlockedAt() {
        return blocked_at;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getName() {
        return name;
    }

    public Date getResolvedAt() {
        return resolved_at;
    }

    public List<Long> getTransitionIds() {
        return transition_ids;
    }

    public Long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public Issue setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Issue setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getIssueTypeId() {
        return issue_type_id;
    }

    public Issue setIssueTypeId(Long issue_type_id) {
        this.issue_type_id = issue_type_id;
        return this;
    }

    public Long getStatusId() {
        return status_id;
    }

    public Issue setStatusId(Long status_id) {
        this.status_id = status_id;
        return this;
    }

    public Long getPriorityId() {
        return priority_id;
    }

    public Issue setPriorityId(Long priority_id) {
        this.priority_id = priority_id;
        return this;
    }

    public Long getOwnerId() {
        return owner_id;
    }

    public Issue setOwnerId(Long owner_id) {
        this.owner_id = owner_id;
        return this;
    }

    public Long getParentId() {
        return parent_id;
    }

    public Long getSubProjectId() {
        return sub_project_id;
    }

    public Date getDueBy() {
        return due_by;
    }

    public void setDueBy(Date dueBy) {
        this.due_by = dueBy;
    }

    public Long getStoryPoints() {
        return story_points;
    }

    public Long getReporterId() {
        return reporter_id;
    }

    public Long getReleaseId() {
        return release_id;
    }

    public Long getSprintId() {
        return sprint_id;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Map<String, Object> getCustomFields() {
        return custom_field;
    }

    public void setCustomFields(Map<String, Object> customFields) {
        this.custom_field = customFields;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }

    public synchronized List<Comment> getComments(boolean refresh) throws FreshReleaseException {
        if (comments == null || refresh) {
            comments = freshRelease.getComments(projectKey, id);
            if (comments != null) {
                for (Comment comment : comments) {
                    comment.setClient(freshRelease, projectKey);
                }
            }
        }
        return comments;
    }

    public synchronized List<Comment> getComments() throws FreshReleaseException {
        return getComments(false);
    }

    public synchronized List<Activity> getActivities(boolean refresh) throws FreshReleaseException {
        if (activities == null || refresh) {
            activities = freshRelease.getActivities(projectKey, id);
        }
        return activities;
    }

    public synchronized List<Activity> getActivities() throws FreshReleaseException {
        return getActivities(false);
    }

    public Comment addComment(String content) throws FreshReleaseException {
        Comment updatedComment = freshRelease.addComment(projectKey, id, new Comment(content));
        updatedComment.setClient(freshRelease, projectKey);
        if (comments != null) {
            comments.add(updatedComment);
        }
        return updatedComment;
    }

    public IssueUpdate update() {
        return new IssueUpdate(freshRelease, projectKey, this);
    }

    void copyFrom(Issue from) {
        if (from == null) {
            return;
        }
        this.comments = from.comments;
        this.activities = from.activities;
        this.id = from.id;
        this.key = from.key;
        this.title = from.title;
        this.name = from.name;
        this.description = from.description;
        this.issue_type_id = from.issue_type_id;
        this.status_id = from.status_id;
        this.priority_id = from.priority_id;
        this.owner_id = from.owner_id;
        this.parent_id = from.parent_id;
        this.sub_project_id = from.sub_project_id;
        this.due_by = from.due_by;
        this.story_points = from.story_points;
        this.reporter_id = from.reporter_id;
        this.release_id = from.release_id;
        this.sprint_id = from.sprint_id;
        this.tags = from.tags;
        this.custom_field = from.custom_field;
        this.created_at = from.created_at;
        this.updated_at = from.updated_at;
        this.resolved = from.resolved;
        this.deleted = from.deleted;
        this.resolved_at = from.resolved_at;
        this.transition_ids = from.transition_ids;
        this.project_id = from.project_id;
        this.start_date = from.start_date;
        this.position = from.position;
        this.eta_flag = from.eta_flag;
        this.epic_id = from.epic_id;
        this.links = from.links;
        this.status_changed_at = from.status_changed_at;
        this.started_at = from.started_at;
        this.blocked = from.blocked;
        this.blocked_at = from.blocked_at;
        if (from.issueTypeMap != null) {
            this.setIssueTypes(from.issueTypeMap.values());
        }
        if (from.statusMap != null) {
            this.setStatuses(from.statusMap.values());
        }
        if (from.userMap != null) {
            this.setUsers(from.userMap.values());
        }
        if (from.priorityMap != null) {
            this.setPriorities(from.priorityMap.values());
        }
        if (from.transitionMap != null) {
            this.setTransitions(from.transitionMap.values());
        }
    }

    public Issue refresh() throws FreshReleaseException {
        Issue issue = freshRelease.getIssue(this.getKey());
        copyFrom(issue);
        return this;
    }

    public void delete() throws FreshReleaseException {
        freshRelease.deleteIssue(projectKey, id);
    }

    public String getProjectKey() {
        return projectKey;
    }

    void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    @Override
    public String toString() {
        return getKey();
    }

    public class IssueUpdate {
        private final FreshRelease client;
        private final String projectKey;
        private final Issue issue;
        private final Issue updateData;

        private IssueUpdate(FreshRelease client, String projectKey, Issue issue) {
            this.client = client;
            this.projectKey = projectKey;
            this.issue = issue;
            this.updateData = new Issue(null, null);
        }

        String getProjectKey() {
            return projectKey;
        }

        public long getId() {
            return this.issue.id;
        }

        public Issue getUpdateData() {
            return updateData;
        }

        public IssueUpdate setTitle(String title) {
            updateData.title = title;
            return this;
        }

        public IssueUpdate setIssueTypeId(Long issueTypeId) {
            updateData.issue_type_id = issueTypeId;
            return this;
        }

        public IssueUpdate setStatusId(Long statusId) {
            updateData.status_id = statusId;
            return this;
        }

        public IssueUpdate setPriorityId(Long priorityId) {
            updateData.priority_id = priorityId;
            return this;
        }

        public IssueUpdate setOwnerId(Long ownerId) {
            updateData.owner_id = ownerId;
            return this;
        }

        public IssueUpdate setTags(List<String> tags) {
            updateData.tags = tags;
            return this;
        }

        public IssueUpdate setCustomFields(Map<String, Object> customFields) {
            updateData.custom_field = customFields;
            return this;
        }

        public IssueUpdate setDescription(String description) {
            updateData.description = description;
            return this;
        }

        public IssueUpdate setDueBy(Date dueBy) {
            updateData.setDueBy(dueBy);
            return this;
        }

        public Issue execute() throws FreshReleaseException {
            issue.copyFrom(client.updateIssue(projectKey, this));
            return issue;
        }

    }

}
