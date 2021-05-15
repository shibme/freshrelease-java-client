package me.shib.lib.freshrelease.api;

import retrofit2.Call;
import retrofit2.http.Query;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

interface FreshReleaseAPI {

    @GET("sessions/current")
    Call<Session> getCurrentSession();

    @POST("{project_key}/issues")
    Call<IssueDetail> createIssue(@Path("project_key") String projectKey,
                                  @Body IssueRequest issueRequest);

    @PUT("{project_key}/issues/{issueId}")
    Call<IssueDetail> updateIssue(@Path("project_key") String projectKey,
                                  @Path("issueId") long issueId,
                                  @Body IssueRequest issueRequest);

    @POST("{project_key}/issues/{issueId}/comments")
    Call<CommentResponse> addComment(@Path("project_key") String projectKey,
                                     @Path("issueId") long issueId,
                                     @Body Comment comment);

    @PUT("{project_key}/comments/{commentId}")
    Call<CommentResponse> updateComment(@Path("project_key") String projectKey,
                                        @Path("commentId") long commentId,
                                        @Body Comment comment);

    @DELETE("{project_key}/comments/{commentId}")
    Call<Void> deleteComment(@Path("project_key") String projectKey,
                             @Path("commentId") long commentId);

    @GET("{project_key}/issues/{issue_key}")
    Call<IssueDetail> getIssue(@Path("project_key") String projectKey, @Path("issue_key") String issueKey);

    @DELETE("{project_key}/issues/{issue_id}")
    Call<Void> deleteIssue(@Path("project_key") String projectKey,
                           @Path("issue_id") long issueKey);

    @GET("{project_key}/issues/{id}/comments")
    Call<CommentList> getComments(@Path("project_key") String projectKey,
                                  @Path("id") long id);

    @GET("{project_key}/activities")
    Call<ActivityList> getActivities(@Path("project_key") String projectKey,
                                     @Query("entity_id") long issueId,
                                     @Query("entity_name") String entity_name);

    @GET("{project_key}/activities")
    Call<ActivityList> getActivities(@Path("project_key") String projectKey,
                                     @Query("entity_id") long issueId,
                                     @Query("entity_name") String entity_name,
                                     @Query("next_token") String next_token);

    @GET("issues")
    Call<IssueList> listIssues(@QueryMap Map<String, String> queryHash,
                               @Query("page") long page,
                               @Query("per_page") long count);

    @GET("statuses")
    Call<StatusList> getStatuses();

    @GET("users/suggest")
    Call<UserList> listUsers(@Query("q") String keyword, @Query("limit") int limit, @Query("page") int page);

    @GET("users/suggest")
    Call<UserList> getUsers(@Query("limit") int limit, @Query("page") int page);

    @GET("priorities")
    Call<PriorityList> getPriorities();

    @GET("issue_types")
    Call<IssueTypeList> getIssueTypes();

    @GET("{project_key}/issue_types")
    Call<IssueTypeList> getIssueTypes(@Path("project_key") String projectKey);

    @GET("projects")
    Call<ProjectList> getProjects(@Query("per_page") int per_page, @Query("page") int page);

    @GET("{project_key}/sub_projects")
    Call<SubProjectList> getSubProjects(@Path("project_key") String projectKey);

    @GET("{project_key}/workflows/{workflowId}")
    Call<WorkflowInfo> getWorkflowInfo(@Path("project_key") String projectKey,
                                       @Path("workflowId") long workflow_id);

    @GET("{project_key}/workflows")
    Call<WorkflowList> getWorkflowList(@Path("project_key") String projectKey);

    final class IssueRequest {
        private final Issue issue;

        IssueRequest(Issue issue) {
            this.issue = issue;
        }
    }

    final class CommentList {
        List<Comment> comments;
    }

    final class CommentResponse {
        Comment comment;
    }

    final class StatusList {
        List<Status> statuses;
    }

    final class PriorityList {
        List<Priority> priorities;
    }

    final class IssueTypeList {
        List<IssueType> issue_types;
    }

    final class SubProjectList {
        List<Project> sub_projects;
    }

    final class Session {

        private User user;

        User getUser() {
            return user;
        }
    }

}
