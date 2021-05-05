package me.shib.lib.freshrelease.api;

import java.util.List;

class ActivityList {

    private List<Activity> activities;
    private Meta meta;

    List<Activity> getActivities() {
        return activities;
    }

    String getNextToken() {
        return meta.next_token;
    }

    class Meta {
        private String next_token;
    }
}
