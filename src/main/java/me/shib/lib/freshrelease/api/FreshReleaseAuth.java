package me.shib.lib.freshrelease.api;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

final class FreshReleaseAuth implements Interceptor {

    private final String apiToken;

    FreshReleaseAuth(String apiToken) {
        this.apiToken = apiToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request requestWithHeaders = originalRequest.newBuilder()
                .header("Authorization", this.apiToken)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .method(originalRequest.method(), originalRequest.body())
                .build();
        return chain.proceed(requestWithHeaders);
    }
}
