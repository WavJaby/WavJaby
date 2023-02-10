package com.wavjaby.github.obj;

import com.wavjaby.github.Api;
import com.wavjaby.json.JsonObject;

import java.time.OffsetDateTime;

@SuppressWarnings("unused")
public class SimpleUser {
    // Required
    final String login;
    final int id;
    final String node_id;
    final String avatar_url;
    final String gravatar_id;
    final String type;
    final boolean site_admin;

    // Not require
    final String name;
    final String email;
    OffsetDateTime starred_at;

    public SimpleUser(JsonObject data, Api api) {
        login = data.getString("login");
        id = data.getInt("id");
        node_id = data.getString("node_id");
        avatar_url = data.getString("avatar_url");
        gravatar_id = data.getString("gravatar_id");
        type = data.getString("type");
        site_admin = data.getBoolean("site_admin");
        name = data.getString("name");
        email = data.getString("email");
        if (data.notNull("starred_at"))
            starred_at = OffsetDateTime.parse(data.getString("starred_at"));
    }

    public String getName() {
        return login;
    }

    public String getDisplayName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getNode_id() {
        return node_id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getGravatar_id() {
        return gravatar_id;
    }

    public String getType() {
        return type;
    }

    public boolean isSite_admin() {
        return site_admin;
    }

    public String getEmail() {
        return email;
    }

    public OffsetDateTime getStarred_at() {
        return starred_at;
    }
}
