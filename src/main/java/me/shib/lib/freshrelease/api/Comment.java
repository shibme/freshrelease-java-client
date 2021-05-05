package me.shib.lib.freshrelease.api;

import java.util.Date;

public final class Comment {

    private transient FreshRelease client;
    private transient String projectKey;

    private Long id;
    private String content;
    private Long user_id;
    private Date created_at;
    private Long commentable_id;
    private String commentable_type;
    private Commentable commentable;

    Comment(String content) {
        this.content = content;
    }

    void setClient(FreshRelease client, String projectKey) {
        this.client = client;
        this.projectKey = projectKey;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Long getUser_id() {
        return user_id;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public Long getCommentable_id() {
        return commentable_id;
    }

    public String getCommentable_type() {
        return commentable_type;
    }

    public void delete() throws FreshReleaseException {
        client.deleteComment(projectKey, id);
    }

    public Comment update(String content) throws FreshReleaseException {
        Comment updatedComment = client.updateComment(projectKey, id, new Comment(content));
        this.id = updatedComment.id;
        this.content = updatedComment.content;
        this.user_id = updatedComment.user_id;
        this.created_at = updatedComment.created_at;
        this.commentable_id = updatedComment.commentable_id;
        this.commentable_type = updatedComment.commentable_type;
        this.commentable = updatedComment.commentable;
        return this;
    }

    @Override
    public String toString() {
        return getContent();
    }

    public class Commentable {
        private Long id;
        private String type;

        public Long getId() {
            return id;
        }

        public String getType() {
            return type;
        }
    }

}
