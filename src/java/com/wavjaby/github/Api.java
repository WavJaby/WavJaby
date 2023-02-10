package com.wavjaby.github;

import com.wavjaby.github.rest.repos.RepoLanguagesAction;
import com.wavjaby.github.rest.users.UserAction;
import com.wavjaby.github.rest.users.UserReposAction;
import com.wavjaby.github.obj.User;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

public class Api implements Runnable {
    private final Map<String, RestRequest<?>> apiCache = new HashMap<>();
    private final String clientId, clientSecret;

    private final ExecutorService resultPool = MyExecutor.newCachedThreadPoolExecutor();
    private final ExecutorService fetchPool = MyExecutor.newFixedThreadExecutor(8);

    private final LinkedBlockingDeque<RestRequest<?>> requestQueue = new LinkedBlockingDeque<>();

    private final Thread thread;
    private final Map<Integer, User> userCache = new HashMap<>();

    public Api(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;

        thread = new Thread(this);
        thread.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }

    @Override
    public void run() {
        while (!thread.isInterrupted()) {
            final RestRequest<?> restRequest;
            try {
                restRequest = requestQueue.take();
            } catch (InterruptedException e) {
                thread.interrupt();
                return;
            }

            fetchPool.submit(() -> {
                try {
                    HttpsURLConnectionImpl conn = (HttpsURLConnectionImpl) new URL("https://api.github.com" + restRequest.path).openConnection();
                    conn.setRequestProperty("X-GitHub-Api-Version", "2022-11-28");
                    conn.setRequestProperty("Accept", "application/vnd.github+json");
                    conn.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ':' + clientSecret).getBytes()));
                    final RestRequest<?> restRequestCache = apiCache.get(restRequest.path);
                    if (restRequestCache != null)
                        conn.setRequestProperty("If-None-Match", restRequestCache.etag);

                    conn.connect();

                    int code = conn.getResponseCode();
                    // Result not change
                    if (code == 304 && restRequestCache != null) {
                        restRequest.resultNotChange(restRequestCache);
                    }
                    // Result changed
                    else if (code == 200) {
                        restRequest.createNewResult(conn.getInputStream(), conn.getHeaderField("etag"));
                        apiCache.put(restRequest.path, restRequest);
                    } else {
                        System.err.println("Unknown response code: " + code + "\n" +
                                new BufferedReader(new InputStreamReader(conn.getErrorStream()))
                                        .lines().collect(Collectors.joining("\n")));
                        return;
                    }
//                System.out.println(conn.getHeaderField("X-Ratelimit-Remaining"));
//                System.out.println(conn.getHeaderField("X-Ratelimit-Used"));
                    resultPool.submit(restRequest::success);
                } catch (IOException e) {
                    resultPool.submit(() -> restRequest.failed.accept(e));
                }
            });
        }
        System.out.println("Request queue close");
    }

    public void addRequest(RestRequest<?> restRequest) {
        requestQueue.offer(restRequest);
    }

    public User getUserByID(int userID) {
        return userCache.get(userID);
    }

    public UserAction getUser(String userName) {
        return new UserAction(userName, this);
    }

    public UserReposAction getUserRepos(String userName) {
        return new UserReposAction(userName, this);
    }

    public RepoLanguagesAction getRepoLanguages(String userName, String repoName) {
        return new RepoLanguagesAction(userName, repoName, this);
    }
}
