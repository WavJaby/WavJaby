package com.wavjaby.github.obj;

import com.wavjaby.github.Api;
import com.wavjaby.github.RestAction;
import com.wavjaby.github.rest.repos.Visibility;
import com.wavjaby.json.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.Map;

@SuppressWarnings("unused")
public class Repo {
    // Required
    final int id;
    final String node_id;
    final String name;
    final String full_name;
    final SimpleUser owner;
    final boolean $private;
    final String description;
    final boolean fork;
    final URL homepage;
    final String language;
    final int forks_count;
    final int stargazers_count;
    final int watchers_count;
    final int size;
    final String default_branch;
    final int open_issues_count;
    final boolean has_issues;
    final boolean has_projects;
    final boolean has_wiki;
    final boolean has_pages;
    final boolean has_downloads;
    final boolean has_discussions;
    final boolean archived;
    final boolean disabled;
    final OffsetDateTime pushed_at;
    final OffsetDateTime created_at;
    final OffsetDateTime updated_at;
    //TODO: final LicenseImpl license;

    // Not require
    boolean is_template;
    String[] topics;
    Visibility visibility;
    //TODO: PermissionsImpl permissions;
    boolean allow_rebase_merge;
    Repo template_repository;
    String temp_clone_token;
    boolean allow_squash_merge;
    boolean allow_auto_merge;
    boolean delete_branch_on_merge;
    boolean allow_merge_commit;
    boolean allow_update_branch;
    boolean use_squash_pr_title_as_default;
    String squash_merge_commit_title;
    String squash_merge_commit_message;
    String merge_commit_title;
    String merge_commit_message;
    boolean allow_forking;
    boolean web_commit_signoff_required;
    User organization;
    Repo parent;
    Repo source;
    String master_branch;
    boolean anonymous_access_enabled;
    //TODO: CodeOfConductImpl code_of_conduct;
    JsonObject security_and_analysis;
    int subscribers_count;
    int network_count;

    private final Api api;

    public Repo(JsonObject data, Map<Integer, SimpleUser> simpleUserCache, Api api) {
        this.api = api;

        id = data.getInt("id");
        node_id = data.getString("node_id");
        name = data.getString("name");
        full_name = data.getString("full_name");
        JsonObject owner = data.getJson("owner");
        int ownerID = owner.getInt("id");
        SimpleUser simpleUser = simpleUserCache.get(ownerID);
        if (simpleUser == null) {
            simpleUser = api.getUserByID(ownerID);
            if (simpleUser == null)
                simpleUserCache.put(ownerID, simpleUser = new SimpleUser(owner, api));
            else
                simpleUserCache.put(ownerID, simpleUser);
        }
        this.owner = simpleUser;
        $private = data.getBoolean("private");
        description = data.getString("description");
        fork = data.getBoolean("fork");
        URL url = null;
        try {
            String homePage = data.getString("homepage");
            if (homePage != null && homePage.length() > 0)
                url = new URL(homePage);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        homepage = url;
        language = data.getString("language");
        forks_count = data.getInt("forks_count");
        stargazers_count = data.getInt("stargazers_count");
        watchers_count = data.getInt("watchers_count");
        size = data.getInt("size");
        default_branch = data.getString("default_branch");
        open_issues_count = data.getInt("open_issues_count");
        has_issues = data.getBoolean("has_issues");
        has_projects = data.getBoolean("has_projects");
        has_wiki = data.getBoolean("has_wiki");
        has_pages = data.getBoolean("has_pages");
        has_downloads = data.getBoolean("has_downloads");
        has_discussions = data.getBoolean("has_discussions");
        archived = data.getBoolean("archived");
        disabled = data.getBoolean("disabled");
        pushed_at = OffsetDateTime.parse(data.getString("pushed_at"));
        created_at = OffsetDateTime.parse(data.getString("created_at"));
        updated_at = OffsetDateTime.parse(data.getString("updated_at"));
        //TODO: license =  data.getJson("license");
    }

    public RestAction<Languages> getUsedLanguages() {
        return api.getRepoLanguages(owner.login, name);
    }

    // Getter
    public int getID() {
        return id;
    }

    public String getNodeID() {
        return node_id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return full_name;
    }

    public SimpleUser getOwner() {
        return owner;
    }

    public boolean isPrivate() {
        return $private;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFork() {
        return fork;
    }

    public URL getHomepage() {
        return homepage;
    }

    public String getLanguage() {
        return language;
    }

    public int getForksCount() {
        return forks_count;
    }

    public int getStargazersCount() {
        return stargazers_count;
    }

    public int getWatchersCount() {
        return watchers_count;
    }

    public int getSize() {
        return size;
    }

    public String getDefaultBranch() {
        return default_branch;
    }

    public int getOpenIssues_count() {
        return open_issues_count;
    }

    public boolean isHasIssues() {
        return has_issues;
    }

    public boolean isHasProjects() {
        return has_projects;
    }

    public boolean isHasWiki() {
        return has_wiki;
    }

    public boolean isHasPages() {
        return has_pages;
    }

    public boolean isHasDownloads() {
        return has_downloads;
    }

    public boolean isHasDiscussions() {
        return has_discussions;
    }

    public boolean isArchived() {
        return archived;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public OffsetDateTime getPushedAt() {
        return pushed_at;
    }

    public OffsetDateTime getCreatedAt() {
        return created_at;
    }

    public OffsetDateTime getUpdatedAt() {
        return updated_at;
    }
}
