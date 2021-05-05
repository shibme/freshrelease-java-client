package me.shib.lib.freshrelease.api;

public final class User {

    private Long id;
    private String name;
    private String avatar_url;
    private String email;
    private boolean account_admin;
    private boolean confirmed;
    private Integer status;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAccount_admin() {
        return account_admin;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Integer getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return getEmail();
    }
}
