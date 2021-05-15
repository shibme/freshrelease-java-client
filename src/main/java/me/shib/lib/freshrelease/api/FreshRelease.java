package me.shib.lib.freshrelease.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.*;

public final class FreshRelease {

    private static final transient String FRESHRELEASE_URL = System.getenv("FRESHRELEASE_URL");
    private static final transient String FRESHRELEASE_API_TOKEN = System.getenv("FRESHRELEASE_API_TOKEN");
    private static final transient String FRESHRELEASE_HTTP_LOGGING = System.getenv("FRESHRELEASE_HTTP_LOGGING");
    private static final transient int defaultPerPage = 250;
    private static final transient Map<String, FreshRelease> clientMap = new HashMap<>();

    private final transient String url;
    private final transient FreshReleaseAPI api;
    private final transient Gson gson;

    private FreshRelease(String url, String apiToken, boolean httpLogging) throws FreshReleaseException {
        if (url == null || url.isEmpty()) {
            throw new FreshReleaseException("The given FreshRelease URL in not set");
        }
        if (apiToken == null || apiToken.isEmpty()) {
            throw new FreshReleaseException("FreshRelease API key is not set");
        }
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(new FreshReleaseAuth(apiToken));
        if (httpLogging) {
            okHttpClientBuilder.addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        OkHttpClient okHttpClient = okHttpClientBuilder.build();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new GsonUTCDateAdapter())
                .create();
        if (url.endsWith("/")) {
            this.url = url;
        } else {
            this.url = url + '/';
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(this.gson))
                .build();
        this.api = retrofit.create(FreshReleaseAPI.class);
        try {
            System.out.println("Authenticated As: " + getCurrentSession().getUser());
        } catch (FreshReleaseException e) {
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("unauthorized")) {
                throw new FreshReleaseException("Unauthorised. Please verify the URL or API token.");
            } else {
                throw e;
            }
        } catch (Exception e) {
            throw new FreshReleaseException(e);
        }
    }

    public static synchronized FreshRelease getInstance(String baseUrl, String apiKey)
            throws FreshReleaseException {
        boolean httpLogging = FRESHRELEASE_HTTP_LOGGING != null &&
                FRESHRELEASE_HTTP_LOGGING.equalsIgnoreCase("TRUE");
        if (baseUrl != null && !baseUrl.isEmpty() &&
                apiKey != null && !apiKey.isEmpty()) {
            String clientIdentifier = baseUrl + apiKey;
            FreshRelease client = clientMap.get(clientIdentifier);
            if (client == null) {
                client = new FreshRelease(baseUrl, apiKey, httpLogging);
                clientMap.put(clientIdentifier, client);
            }
            return client;
        }
        return null;
    }

    public static FreshRelease getInstance() throws FreshReleaseException {
        if (FRESHRELEASE_URL == null || FRESHRELEASE_URL.isEmpty()) {
            throw new FreshReleaseException("Please set FRESHRELEASE_URL environment variable");
        }
        if (FRESHRELEASE_API_TOKEN == null || FRESHRELEASE_API_TOKEN.isEmpty()) {
            throw new FreshReleaseException("Please set FRESHRELEASE_API_TOKEN environment variable");
        }
        return getInstance(FRESHRELEASE_URL, FRESHRELEASE_API_TOKEN);
    }

    private void handleResponse(Response response) throws FreshReleaseException {
        if (response.code() < 200 || response.code() > 250) {
            StringBuilder message = new StringBuilder();
            try {
                String errorMessage = response.errorBody().string();
                message.append(errorMessage);
            } catch (Exception ignored) {
            }
            if (response.body() != null) {
                if (!message.toString().isEmpty()) {
                    message.append("\n");
                }
                message.append(gson.toJson(response.body()));
            }
            throw new FreshReleaseException(message.toString());
        }
    }

    public String getUrl() {
        return url;
    }

    public FreshReleaseAPI.Session getCurrentSession() throws FreshReleaseException {
        try {
            Response<FreshReleaseAPI.Session> response = api.getCurrentSession().execute();
            handleResponse(response);
            return response.body();
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

    public UserList searchUsersByName(String keyword, int limit, int page) throws FreshReleaseException {
        try {
            Response<UserList> response = api.listUsers(keyword, limit, page).execute();
            handleResponse(response);
            return response.body();
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

    public UserList getUsers(int limit, int page) throws FreshReleaseException {
        try {
            Response<UserList> response = api.getUsers(limit, page).execute();
            handleResponse(response);
            return response.body();
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

    public List<User> getUsers() throws FreshReleaseException {
        UserList userList = getUsers(defaultPerPage, 1);
        List<User> users = new ArrayList<>(userList.getUsers());
        for (int i = 2; i <= userList.getMeta().getTotal_pages(); i++) {
            userList = getUsers(defaultPerPage, i);
            users.addAll(userList.getUsers());
        }
        return users;
    }

    public List<Priority> getPriorities() throws FreshReleaseException {
        try {
            Response<FreshReleaseAPI.PriorityList> response = api.getPriorities().execute();
            handleResponse(response);
            return response.body().priorities;
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

    public List<Status> getStatuses() throws FreshReleaseException {
        try {
            Response<FreshReleaseAPI.StatusList> response = api.getStatuses().execute();
            handleResponse(response);
            return response.body().statuses;
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

    public List<IssueType> getIssueTypes() throws FreshReleaseException {
        try {
            Response<FreshReleaseAPI.IssueTypeList> response = api.getIssueTypes().execute();
            handleResponse(response);
            return response.body().issue_types;
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

    public List<IssueType> getIssueTypes(String projectKey) throws FreshReleaseException {
        try {
            Response<FreshReleaseAPI.IssueTypeList> response = api.getIssueTypes(projectKey).execute();
            handleResponse(response);
            return response.body().issue_types;
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

    public ProjectList getProjects(int per_page, int page) throws FreshReleaseException {
        try {
            Response<ProjectList> response = api.getProjects(per_page, page).execute();
            handleResponse(response);
            return response.body();
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

    public List<Project> getProjects() throws FreshReleaseException {
        List<Project> projects = new ArrayList<>();
        ProjectList projectList = getProjects(defaultPerPage, 1);
        projects.addAll(projectList.getProjects());
        for (int i = 2; i <= projectList.getMeta().getTotal_pages(); i++) {
            projectList = getProjects(defaultPerPage, i);
            projects.addAll(projectList.getProjects());
        }
        return projects;
    }

    public List<Project> getSubProjects(String projectKey) throws FreshReleaseException {
        try {
            Response<FreshReleaseAPI.SubProjectList> response = api.getSubProjects(projectKey).execute();
            handleResponse(response);
            return response.body().sub_projects;
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

    public WorkflowInfo getWorkflowInfo(String projectKey, long workflow_id) throws FreshReleaseException {
        try {
            Response<WorkflowInfo> response = api.getWorkflowInfo(projectKey, workflow_id).execute();
            handleResponse(response);
            return response.body();
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

    public WorkflowList getWorkflowList(String projectKey) throws FreshReleaseException {
        try {
            Response<WorkflowList> response = api.getWorkflowList(projectKey).execute();
            handleResponse(response);
            return response.body();
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

    public Issue createIssue(String projectKey, Issue issue) throws FreshReleaseException {
        try {
            Response<IssueDetail> response = api.createIssue(projectKey,
                    new FreshReleaseAPI.IssueRequest(issue)).execute();
            handleResponse(response);
            IssueDetail issueDetail = response.body();
            if (issueDetail != null) {
                issue.copyFrom(issueDetail.getIssue());
                issue.setFreshRelease(this);
                issue.setProjectKey(projectKey);
                issue.setIssueTypes(issueDetail.getIssue_types());
                issue.setPriorities(issueDetail.getPriorities());
                issue.setStatuses(issueDetail.getStatuses());
                issue.setUsers(issueDetail.getUsers());
                issue.setTransitions(issueDetail.getTransitions());
                return issue;
            }
            return null;
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

    Issue updateIssue(String projectKey, Issue.IssueUpdate issueUpdate) throws FreshReleaseException {
        try {
            Response<IssueDetail> response = api.updateIssue(projectKey, issueUpdate.getId(),
                    new FreshReleaseAPI.IssueRequest(issueUpdate.getUpdateData())).execute();
            handleResponse(response);
            IssueDetail issueDetail = response.body();
            if (issueDetail != null) {
                Issue issue = issueDetail.getIssue();
                issue.setFreshRelease(this);
                issue.setProjectKey(projectKey);
                issue.setIssueTypes(issueDetail.getIssue_types());
                issue.setPriorities(issueDetail.getPriorities());
                issue.setStatuses(issueDetail.getStatuses());
                issue.setUsers(issueDetail.getUsers());
                issue.setTransitions(issueDetail.getTransitions());
                return issue;
            }
            return null;
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

    public Issue getIssue(String issueKey) throws FreshReleaseException {
        try {
            String projectKey = issueKey.split("-")[0];
            Response<IssueDetail> response = api.getIssue(projectKey, issueKey).execute();
            handleResponse(response);
            IssueDetail issueDetail = response.body();
            if (issueDetail != null) {
                Issue issue = issueDetail.getIssue();
                issue.setFreshRelease(this);
                issue.setProjectKey(projectKey);
                issue.setIssueTypes(issueDetail.getIssue_types());
                issue.setPriorities(issueDetail.getPriorities());
                issue.setStatuses(issueDetail.getStatuses());
                issue.setUsers(issueDetail.getUsers());
                issue.setTransitions(issueDetail.getTransitions());
                return issue;
            }
            return null;
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

    void deleteIssue(String projectKey, long issueId) throws FreshReleaseException {
        try {
            Response<Void> response = api.deleteIssue(projectKey, issueId).execute();
            handleResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public IssueList listIssues(Query query, long page, long issuesPerPage) throws FreshReleaseException {
        try {
            Response<IssueList> response;
            if (query == null) {
                query = new Query();
            }
            query.includeRequiredFields();
            response = api.listIssues(query.toQueryMap(), page, issuesPerPage).execute();
            handleResponse(response);
            IssueList issueList = response.body();
            if (issueList != null) {
                issueList.backfill(this);
            }
            return issueList;
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

    public List<Issue> listIssues(Query query) throws FreshReleaseException {
        if (query == null) {
            query = new Query();
        }
        Map<String, Issue> issueMap = new LinkedHashMap<>();
        long page = 1;
        long totalPages = 1;
        while (page <= totalPages) {
            IssueList issueList = listIssues(query, page, defaultPerPage);
            for (Issue issue : issueList.getIssues()) {
                issueMap.put(issue.getKey(), issue);
            }
            page++;
            totalPages = issueList.getMeta().getTotal_pages();
        }
        return new ArrayList<>(issueMap.values());
    }

    Comment addComment(String projectKey, long issueId, Comment comment) throws FreshReleaseException {
        try {
            Response<FreshReleaseAPI.CommentResponse> response = api.addComment(projectKey, issueId, comment).execute();
            handleResponse(response);
            return response.body().comment;
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

    Comment updateComment(String projectKey, long commentId, Comment comment) throws FreshReleaseException {
        try {
            Response<FreshReleaseAPI.CommentResponse> response = api.updateComment(projectKey, commentId, comment).execute();
            handleResponse(response);
            return response.body().comment;
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

    void deleteComment(String projectKey, long commentId) throws FreshReleaseException {
        try {
            Response<Void> response = api.deleteComment(projectKey, commentId).execute();
            handleResponse(response);
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

    List<Comment> getComments(String projectKey, long issueId) throws FreshReleaseException {
        try {
            Response<FreshReleaseAPI.CommentList> response = api.getComments(projectKey, issueId).execute();
            handleResponse(response);
            return response.body().comments;
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

    List<Activity> getActivities(String projectKey, long issueId) throws FreshReleaseException {
        try {
            List<Activity> activities = new ArrayList<>();
            Response<ActivityList> response = api.getActivities(projectKey, issueId, "issue").execute();
            handleResponse(response);
            ActivityList activityList = response.body();
            while (activityList != null && activityList.getNextToken() != null) {
                activities.addAll(activityList.getActivities());
                response = api.getActivities(projectKey, issueId, "issue", activityList.getNextToken()).execute();
                handleResponse(response);
                activityList = response.body();
            }
            if (activityList != null) {
                activities.addAll(activityList.getActivities());
            }
            return activities;
        } catch (IOException e) {
            throw new FreshReleaseException(e);
        }
    }

}
