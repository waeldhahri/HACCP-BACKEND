package com.example.haccpbackend.scrap;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class JobScraperService {

    private static final String API_URL = "https://cgm.wd3.myworkdayjobs.com/wday/cxs/cgm/cgm/jobs";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<String> getMatchingJobs() {
        List<String> jobs = new ArrayList<>();

        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Headers nécessaires pour Workday
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            conn.setRequestProperty("Referer", "https://cgm.wd3.myworkdayjobs.com/de-DE/cgm");
            conn.setRequestProperty("Accept-Language", "de-DE,de;q=0.9,en;q=0.8");

            // 🔹 Envoyer un corps JSON vide pour initialiser la recherche
            String emptyJson = "{}";
            try (OutputStream os = conn.getOutputStream()) {
                os.write(emptyJson.getBytes());
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.out.println("❌ Erreur HTTP: " + responseCode);
                return jobs;
            }

            // Lire le JSON de réponse
            Scanner sc = new Scanner(conn.getInputStream());
            StringBuilder jsonText = new StringBuilder();
            while (sc.hasNext()) jsonText.append(sc.nextLine());
            sc.close();

            JsonNode root = objectMapper.readTree(jsonText.toString());
            JsonNode jobPostings = root.path("jobPostings");

            for (JsonNode job : jobPostings) {
                String title = job.path("title").asText().toLowerCase();
                String location = job.path("locationsText").asText().toLowerCase();

                if (location.contains("koblenz") &&
                        (title.contains("junior") || title.contains("spring boot") || title.contains("spring"))) {
                    String jobUrl = "https://cgm.wd3.myworkdayjobs.com/de-DE/cgm" + job.path("externalPath").asText();
                    jobs.add(job.path("title").asText() + " ➝ " + jobUrl);
                }
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobs;
    }
}
