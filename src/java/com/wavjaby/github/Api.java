package com.wavjaby.github;

import com.wavjaby.github.api.users.UserRepos;
import com.wavjaby.github.obj.UserData;
import com.wavjaby.json.JsonArray;
import com.wavjaby.json.JsonObject;
import com.wavjaby.github.api.repos.RepoLanguages;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Api {
    private final Map<String, GithubApiResult> apiCache = new HashMap<>();
    private final Map<Long, UserData> userCache = new HashMap<>();
    public final String clientId, clientSecret;

    public Api(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    private HttpURLConnection getApi(String path, GithubApiResult resultCache) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://api.github.com" + path).openConnection();
            connection.setRequestProperty("X-GitHub-Api-Version", "2022-11-28");
            connection.setRequestProperty("Accept", "application/vnd.github+json");
            connection.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ':' + clientSecret).getBytes()));

            if (resultCache != null) {
                connection.setRequestProperty("If-None-Match", resultCache.etag);
//                System.out.println(resultCache.etag);
            }

            connection.connect();
            if (connection.getResponseCode() == 304)
                return null;

//            for (Map.Entry<String, List<String>> i : connection.getHeaderFields().entrySet())
//                System.out.println(i.getKey() + ": " + i.getValue());
//            System.out.println(connection.getHeaderField("X-Ratelimit-Limit"));
            System.out.println(connection.getHeaderField("X-Ratelimit-Remaining"));
            System.out.println(connection.getHeaderField("X-Ratelimit-Used"));
//            System.out.println(connection.getHeaderField("X-Ratelimit-Reset"));

            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserRepos getUserRepos(String userName) {
        String path = "/users/" + userName + "/repos";
        GithubApiResult resultCache = apiCache.get(path);
        HttpURLConnection connection = getApi(path, resultCache);
        if (connection == null)
            return (UserRepos) resultCache;

        UserRepos userRepos;
        try {
            userRepos = new UserRepos(
                    new JsonArray(connection.getInputStream()),
                    connection.getHeaderField("Etag")
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        apiCache.put(path, userRepos);
        return userRepos;
    }

    public RepoLanguages getRepoLanguages(String userName, String repoName) {
        String path = "/repos/" + userName + '/' + repoName + "/languages";
        GithubApiResult resultCache = apiCache.get(path);
        HttpURLConnection connection = getApi(path, resultCache);
        if (connection == null)
            return (RepoLanguages) resultCache;

        RepoLanguages repoLanguages;
        try {
            repoLanguages = new RepoLanguages(
                    new JsonObject(connection.getInputStream()),
                    connection.getHeaderField("Etag")
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        apiCache.put(path, repoLanguages);
        return repoLanguages;
    }
}
