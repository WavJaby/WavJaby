package com.wavjaby.github;

import java.io.InputStream;
import java.util.function.Consumer;

public class RestRequest<T> {
    final String path;
    final Consumer<? super T> success;
    final Consumer<? super Throwable> failed;
    final RestAction<T> restAction;

    String etag;
    T result;

    public RestRequest(String path, RestAction<T> restAction, Consumer<? super T> success, Consumer<? super Throwable> failed) {
        this.path = path;
        this.restAction = restAction;
        this.success = success;
        this.failed = failed;
    }

    void createNewResult(InputStream inputStream, String etag) {
        this.etag = etag;
        result = restAction.newResult(inputStream);
    }

    void resultNotChange(RestRequest<?> restResultCache) {
        result = (T) restResultCache.result;
    }

    void success() {
        success.accept(result);
    }
}
