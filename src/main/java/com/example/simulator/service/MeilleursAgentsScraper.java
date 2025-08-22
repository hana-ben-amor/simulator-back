package com.example.simulator.service;

import com.example.simulator.data.CityData;
import com.example.simulator.data.CityRepository;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.OptionalDouble;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MeilleursAgentsScraper {
    private final OkHttpClient client;
    @Value("${app.scraper.userAgent}")
    String userAgent;
    public OptionalDouble fetchRentPerM2(String city) {
        CityData data = CityRepository.citySlug.get(city.toUpperCase());
        if (data == null) return OptionalDouble.empty();

        var url = "https://www.meilleursagents.com/prix-immobilier/" + data.slug + "/";
        var req = new Request.Builder().url(url).header("User-Agent", userAgent).build();

        try (var resp = client.newCall(req).execute()) {
            if (!resp.isSuccessful() || resp.body()==null) {
                return OptionalDouble.of(data.rentPerM2); // fallback
            }
            var html = resp.body().string();
            var doc = Jsoup.parse(html);

            var text = doc.selectFirst(":matchesOwn((?i)loyer moyen.*€)").text();
            var matcher = Pattern.compile("(\\d+[\\.,]?\\d*)\\s*€").matcher(text);
            if (matcher.find()) {
                return OptionalDouble.of(Double.parseDouble(matcher.group(1).replace(",", ".")));
            }
        } catch (Exception e) {
            return OptionalDouble.of(data.rentPerM2); // fallback en cas d’erreur
        }
        return OptionalDouble.of(data.rentPerM2); // fallback si rien trouvé
    }

}