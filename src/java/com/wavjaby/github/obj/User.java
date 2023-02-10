package com.wavjaby.github.obj;

import com.wavjaby.github.Api;
import com.wavjaby.json.JsonObject;

import java.time.OffsetDateTime;

@SuppressWarnings("unused")
public class User extends SimpleUser {
    // Required
    final String company;
    final String blog;
    final String location;
    final Boolean hireable; // Can be null
    final String bio;
    final int public_repos;
    final int public_gists;
    final int followers;
    final int following;
    final OffsetDateTime created_at;
    final OffsetDateTime updated_at;

    // Not require
    String twitter_username;
    int private_gists;
    int total_private_repos;
    int owned_private_repos;
    int disk_usage;
    int collaborators;
    boolean two_factor_authentication;
    //TODO: PlaData plan;
    OffsetDateTime suspended_at;
    boolean business_plus;
    String ldap_dn;

    public User(JsonObject data, Api api) {
        super(data, api);
        company = data.getString("company");
        blog = data.getString("blog");
        location = data.getString("location");
        hireable = (Boolean) data.getObject("hireable");
        bio = data.getString("bio");
        public_repos = data.getInt("public_repos");
        public_gists = data.getInt("public_gists");
        followers = data.getInt("followers");
        following = data.getInt("following");
        created_at = OffsetDateTime.parse(data.getString("created_at"));
        updated_at = OffsetDateTime.parse(data.getString("updated_at"));
    }

    public String getCompany() {
        return company;
    }

    public String getBlog() {
        return blog;
    }

    public String getLocation() {
        return location;
    }

    public Boolean getHireable() {
        return hireable;
    }

    public String getBio() {
        return bio;
    }

    public int getPublicRepos() {
        return public_repos;
    }

    public int getPublicGists() {
        return public_gists;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public OffsetDateTime getCreatedAt() {
        return created_at;
    }

    public OffsetDateTime getUpdatedAt() {
        return updated_at;
    }
}
