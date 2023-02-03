package com.wavjaby.github.api.users;

import com.wavjaby.github.GithubApiResult;
import com.wavjaby.github.obj.SimpleUserData;
import com.wavjaby.json.JsonArray;
import com.wavjaby.json.JsonObject;
import com.wavjaby.github.obj.RepoData;

import java.util.*;

public class UserRepos extends GithubApiResult implements Iterable<RepoData> {
    private final List<RepoData> list;
    private final Map<Long, SimpleUserData> simpleUserCache;

    public UserRepos(JsonArray data, String etag) {
        super(etag);
        list = new ArrayList<>();
        simpleUserCache = new HashMap<>();
        for (Object i : data) {
            RepoData repo = new RepoData((JsonObject) i, simpleUserCache);
            list.add(repo);
        }
    }

    @Override
    public Iterator<RepoData> iterator() {
        return list.listIterator();
    }
}
