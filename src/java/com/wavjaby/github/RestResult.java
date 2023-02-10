package com.wavjaby.github;

public abstract class RestResult {
    final String etag;

    protected RestResult(String etag) {
        this.etag = etag;
    }
}
