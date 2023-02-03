package com.wavjaby.github;

public abstract class GithubApiResult {
    final String etag;

    protected GithubApiResult(String etag) {
        this.etag = etag;
    }
}
