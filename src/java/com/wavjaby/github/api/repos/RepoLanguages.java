package com.wavjaby.github.api.repos;

import com.wavjaby.github.GithubApiResult;
import com.wavjaby.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class RepoLanguages extends GithubApiResult {
    final Map<String, Long> usedLanguages;
    public RepoLanguages(JsonObject data, String etag) {
        super(etag);
        usedLanguages = new HashMap<>();
        for (Map.Entry<String, Object> i : data)
            usedLanguages.put(i.getKey(), (Long) i.getValue());
    }
}
