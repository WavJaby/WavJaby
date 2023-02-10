package com.wavjaby.github.rest.users;

import com.wavjaby.github.Api;
import com.wavjaby.github.RestAction;
import com.wavjaby.github.RestRequest;
import com.wavjaby.github.obj.User;
import com.wavjaby.json.JsonObject;

import java.io.InputStream;
import java.util.function.Consumer;

public class UserAction extends RestAction<User>{
    private final Api api;
    private final String path;

    public UserAction(String userName, Api api) {
        this.api = api;
        this.path = "/users/" + userName;
    }

    @Override
    protected User newResult(InputStream inputStream) {
        return new User(new JsonObject(inputStream), api);
    }

    @Override
    protected void createRestRequest(Consumer<? super User> success, Consumer<? super Throwable> failed) {
        api.addRequest(new RestRequest<>(path, this, success, failed));
    }
}
