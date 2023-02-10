package com.wavjaby.github.obj;

import com.wavjaby.github.Api;
import com.wavjaby.json.JsonObject;

import java.time.OffsetDateTime;
import java.util.Map;

public class Event {
    // Required
    final String id;
    final Type type;
    final Actor actor;
    final SimpleRepo repo;
    final Payload payload;
    final boolean $public;
    final OffsetDateTime created_at;

    // Not require
    Actor org;


    private final Api api;
    private final Map<Long, SimpleUser> userCache;

    public Event(JsonObject data, Map<Long, SimpleUser> userCache, Api api) {
        this.api = api;
        this.userCache = userCache;

        id = data.getString("id");
        type = Type.valueOf(data.getString("type"));
        actor = new Actor(data);
        repo = new SimpleRepo(data);
        payload = new Payload(data);
        $public = data.getBoolean("public");
        created_at = OffsetDateTime.parse(data.getString("created_at"));
    }

    public static class Actor {
        final int id;
        final String login;
        final String gravatar_id;
        final String avatar_url;

        String display_login;

        public Actor(JsonObject data) {
            id = data.getInt("id");
            login = data.getString("login");
            gravatar_id = data.getString("gravatar_id");
            avatar_url = data.getString("avatar_url");

            display_login = data.getString("display_login");
        }

        public int getID() {
            return id;
        }

        public String getName() {
            return login;
        }

        public String getGravatarID() {
            return gravatar_id;
        }

        public String getAvatarURL() {
            return avatar_url;
        }

        public String getDisplayName() {
            return display_login;
        }
    }

    public static class SimpleRepo {
        final String id;
        final String name;

        public SimpleRepo(JsonObject data) {
            this.id = data.getString("id");
            this.name = data.getString("name");
        }

        public String getID() {
            return id;
        }

        public String getFullName() {
            return name;
        }
    }

    public static class Payload {
        String action;
        String issue;
        String comment;
        String pages;

        // TODO: Read payload
        public Payload(JsonObject data) {

        }
    }

    public enum Type {
        WatchEvent,
        PushEvent,
        CreateEvent
    }
}
