package com.wavjaby.github.obj;

import com.wavjaby.github.Api;
import com.wavjaby.json.JsonArray;
import com.wavjaby.json.JsonObject;

import java.util.*;

public class Repos implements Iterable<Repo> {
    List<Repo> list;

    public Repos(JsonArray data, Api api) {
        list = new ArrayList<>();
        Map<Integer, SimpleUser> simpleUserCache = new HashMap<>();
        for (Object i : data) {
            Repo repo = new Repo((JsonObject) i, simpleUserCache, api);
            list.add(repo);
        }
    }

    @Override
    public Iterator<Repo> iterator() {
        return list.listIterator();
    }

    public int getSize() {
        return list.size();
    }
}
