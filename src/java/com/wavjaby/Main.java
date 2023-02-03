package com.wavjaby;

import com.wavjaby.github.Api;
import com.wavjaby.github.api.users.UserRepos;
import com.wavjaby.github.obj.RepoData;

public class Main {
    Main() {
        Api api = new Api();

        long startTime = System.currentTimeMillis();

        UserRepos userRepos = api.getUserRepos("WavJaby");
        userRepos = api.getUserRepos("WavJaby");

        for (RepoData repoData : userRepos) {
//            System.out.println(repoData.getLanguage());

//                connection = (HttpURLConnection) new URL(languagesUrl).openConnection();
//                connection.setRequestProperty("X-GitHub-Api-Version", "2022-11-28");
//                connection.setRequestProperty("Accept", "application/vnd.github+json");
//                JsonObject languageData = new JsonObject(connection.getInputStream());
//                System.out.println(languageData.toStringBeauty());
        }

        System.out.println((System.currentTimeMillis() - startTime) + "ms");
    }

    public static void main(String[] args) {
        new Main();
    }
}
