package com.wavjaby.github.rest.users;

import com.wavjaby.github.Api;
import com.wavjaby.github.RestAction;
import com.wavjaby.github.RestRequest;
import com.wavjaby.github.obj.Repos;
import com.wavjaby.json.JsonArray;

import java.io.InputStream;
import java.util.function.Consumer;

public class UserReposAction extends RestAction<Repos> {
    private final Api api;
    private final String path;

    public UserReposAction(String userName, Api api) {
        this.api = api;
        this.path = "/users/" + userName + "/repos";
    }

    @Override
    protected Repos newResult(InputStream inputStream) {
        return new Repos(new JsonArray(inputStream), api);
    }

    @Override
    protected void createRestRequest(Consumer<? super Repos> success, Consumer<? super Throwable> failed) {
        api.addRequest(new RestRequest<>(path, this, success, failed));
    }
}
