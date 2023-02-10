package com.wavjaby.github.rest.repos;

import com.wavjaby.github.Api;
import com.wavjaby.github.RestAction;
import com.wavjaby.github.RestRequest;
import com.wavjaby.github.obj.Languages;
import com.wavjaby.json.JsonObject;

import java.io.InputStream;
import java.util.function.Consumer;

public class RepoLanguagesAction extends RestAction<Languages> {
    private final Api api;
    private final String path;

    public RepoLanguagesAction(String userName, String repoName, Api api) {
        this.api = api;
        this.path = "/repos/" + userName + '/' + repoName + "/languages";
    }

    @Override
    protected Languages newResult(InputStream inputStream) {
        return new Languages(new JsonObject(inputStream), api);
    }

    @Override
    protected void createRestRequest(Consumer<? super Languages> success, Consumer<? super Throwable> failed) {
        api.addRequest(new RestRequest<>(path, this, success, failed));
    }
}
