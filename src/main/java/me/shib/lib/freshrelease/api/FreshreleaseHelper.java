package me.shib.lib.freshrelease.api;

import java.util.HashMap;
import java.util.Map;

public final class FreshreleaseHelper {

    private final transient FreshRelease fr;
    private transient Map<String, Project> projectKeyMap;
    private transient Map<Long, Project> projectIdMap;
    private transient Map<String, User> userEmailMap;
    private transient Map<Long, User> userIdMap;
    private transient Map<String, Status> statusLabelMap;
    private transient Map<Long, Status> statusIdMap;
    private transient Map<String, Priority> priorityLabelMap;
    private transient Map<Long, Priority> priorityIdMap;
    private transient Map<String, IssueType> issueTypeLabelMap;
    private transient Map<Long, IssueType> issueTypeIdMap;

    public FreshreleaseHelper(FreshRelease fr) {
        this.fr = fr;
    }

    private synchronized void syncProjectMaps() throws FreshReleaseException {
        if (projectKeyMap == null || projectIdMap == null) {
            projectKeyMap = new HashMap<>();
            projectIdMap = new HashMap<>();
            for (Project project : fr.getProjects()) {
                projectKeyMap.put(project.getKey(), project);
                projectIdMap.put(project.getId(), project);
            }
        }
    }

    private void initProjectMaps() throws FreshReleaseException {
        if (projectKeyMap == null || projectIdMap == null) {
            syncProjectMaps();
        }
    }

    private synchronized void syncUserMaps() throws FreshReleaseException {
        if (userEmailMap == null || userIdMap == null) {
            userEmailMap = new HashMap<>();
            userIdMap = new HashMap<>();
            for (User user : fr.getUsers()) {
                userEmailMap.put(user.getEmail(), user);
                userIdMap.put(user.getId(), user);
            }
        }
    }

    private void initUserMaps() throws FreshReleaseException {
        if (userEmailMap == null || userIdMap == null) {
            syncUserMaps();
        }
    }

    private synchronized void syncStatusMaps() throws FreshReleaseException {
        if (statusLabelMap == null || statusIdMap == null) {
            statusLabelMap = new HashMap<>();
            statusIdMap = new HashMap<>();
            for (Status status : fr.getStatuses()) {
                statusLabelMap.put(status.getLabel(), status);
                statusIdMap.put(status.getId(), status);
            }
        }
    }

    private void initStatusMaps() throws FreshReleaseException {
        if (statusLabelMap == null || statusIdMap == null) {
            syncStatusMaps();
        }
    }

    private synchronized void syncPriorityMaps() throws FreshReleaseException {
        if (priorityLabelMap == null || priorityIdMap == null) {
            priorityLabelMap = new HashMap<>();
            priorityIdMap = new HashMap<>();
            for (Priority priority : fr.getPriorities()) {
                priorityLabelMap.put(priority.getLabel(), priority);
                priorityIdMap.put(priority.getId(), priority);
            }
        }
    }

    private void initPriorityMaps() throws FreshReleaseException {
        if (priorityLabelMap == null || priorityIdMap == null) {
            syncPriorityMaps();
        }
    }

    private synchronized void syncIssueTypeMaps() throws FreshReleaseException {
        if (issueTypeLabelMap == null || issueTypeIdMap == null) {
            issueTypeLabelMap = new HashMap<>();
            issueTypeIdMap = new HashMap<>();
            for (IssueType issueType : fr.getIssueTypes()) {
                issueTypeLabelMap.put(issueType.getLabel(), issueType);
                issueTypeIdMap.put(issueType.getId(), issueType);
            }
        }
    }

    private void initIssueTypeMaps() throws FreshReleaseException {
        if (issueTypeLabelMap == null || issueTypeIdMap == null) {
            syncIssueTypeMaps();
        }
    }

    public Project getProject(String projectKey) throws FreshReleaseException {
        initProjectMaps();
        return projectKeyMap.get(projectKey);
    }

    public Project getProject(Long id) throws FreshReleaseException {
        initProjectMaps();
        return projectIdMap.get(id);
    }

    public User getUser(String email) throws FreshReleaseException {
        initUserMaps();
        return userEmailMap.get(email);
    }

    public User getUser(Long id) throws FreshReleaseException {
        initUserMaps();
        return userIdMap.get(id);
    }

    public Status getStatus(String label) throws FreshReleaseException {
        initStatusMaps();
        return statusLabelMap.get(label);
    }

    public Status getStatus(Long id) throws FreshReleaseException {
        initStatusMaps();
        return statusIdMap.get(id);
    }

    public Priority getPriority(String label) throws FreshReleaseException {
        initPriorityMaps();
        return priorityLabelMap.get(label);
    }

    public Priority getPriority(Long id) throws FreshReleaseException {
        initPriorityMaps();
        return priorityIdMap.get(id);
    }

    public IssueType getIssueType(String label) throws FreshReleaseException {
        initIssueTypeMaps();
        return issueTypeLabelMap.get(label);
    }

    public IssueType getIssueType(Long id) throws FreshReleaseException {
        initIssueTypeMaps();
        return issueTypeIdMap.get(id);
    }

}
