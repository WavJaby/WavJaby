package com.wavjaby;

import com.wavjaby.github.Api;
import com.wavjaby.json.JsonObject;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

import java.net.URL;

public class Main {
    Main() {
        Api api = new Api("e2e712936ef93498a6d4", "c375e65785c3dcfd7f533640a3dd7a491739083d");

        long startTime = System.currentTimeMillis();

        try {
            HttpsURLConnectionImpl conn = (HttpsURLConnectionImpl) new URL("https://api.github.com/graphql").openConnection();
            conn.setRequestProperty("X-GitHub-Api-Version", "2022-11-28");
            conn.setRequestProperty("Accept", "application/vnd.github+json");
            conn.setRequestProperty("Authorization", "Bearer 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");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.getOutputStream().write(new JsonObject().put("query", "" +
                            "query ($userName: String!) {\n" +
                            "  user(login: $userName) {\n" +
                            "    login\n" +
                            "    contributionsCollection {\n" +
                            "      contributionCalendar {\n" +
                            "        colors\n" +
                            "        totalContributions\n" +
                            "        weeks {\n" +
                            "          firstDay\n" +
                            "          contributionDays {\n" +
                            "            color\n" +
                            "            contributionCount\n" +
                            "            date\n" +
                            "          }\n" +
                            "        }\n" +
                            "      }\n" +
                            "    }\n" +
                            "  }\n" +
                            "}")
                    .put("variables", new JsonObject()
                            .put("userName", "WavJaby")
                    )
                    .toString().getBytes());

            JsonObject json = new JsonObject(conn.getInputStream());
//            System.out.println(json.get("data").get("user").toStringBeauty());
            JsonObject contributions = json.get("data").get("user").get("contributionsCollection");
            System.out.println(contributions.get("contributionCalendar").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


//        api.getUserRepos("WavJaby").queue(repos -> {
//            Map<String, Long> totalLanguage = new HashMap<>();
//            CountDownLatch count = new CountDownLatch(repos.getSize());
//            for (Repo repo : repos) {
//                if (repo.isFork()) {
//                    count.countDown();
//                    continue;
//                }
//                repo.getUsedLanguages().queue(languages -> {
//                    for (Map.Entry<String, Long> lang : languages.getLanguages().entrySet()) {
//                        Long langLen = totalLanguage.getOrDefault(lang.getKey(), 0L);
//                        langLen += lang.getValue();
//                        totalLanguage.put(lang.getKey(), langLen);
//                    }
//                    count.countDown();
//                }, e -> count.countDown());
//            }
//            try {
//                count.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                return;
//            }
//
//            List<Map.Entry<String, Long>> languages = totalLanguage.entrySet().stream()
//                    .sorted((a, b) -> (int) (b.getValue() - a.getValue()))
//                    .collect(Collectors.toList());
//
//            System.out.println(languages.size());
//            String result = SvgBuilder.languagesToPieChartSvg(languages, 10);
//            try {
//                FileWriter fileWriter = new FileWriter("out2.svg");
//                fileWriter.write(result);
//                fileWriter.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("Done");
//        });


        System.out.println((System.currentTimeMillis() - startTime) + "ms");
    }

    public static void main(String[] args) {
        new Main();
    }
}
