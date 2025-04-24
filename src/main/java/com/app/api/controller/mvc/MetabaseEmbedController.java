package com.app.api.controller.mvc;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MetabaseEmbedController {

    private static final String METABASE_SECRET_KEY = "your_metabase_secret_key";
    private static final String METABASE_SITE_URL = "http://localhost:3000";

    @GetMapping("/api/v2/web/embed-dashboard")
    public Map<String, String> getSignedMetabaseUrl(@RequestParam("dashboardId") Integer dashboardId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("resource", Map.of("dashboard", dashboardId));
        payload.put("params", new HashMap<>());
        payload.put("exp", (new Date().getTime() / 1000) + (60 * 60)); // 1 hour expiration

        String token = Jwts.builder()
                .setClaims(payload)
                .signWith(SignatureAlgorithm.HS256, METABASE_SECRET_KEY.getBytes())
                .compact();

        String iframeUrl = METABASE_SITE_URL + "/embed/dashboard/" + token + "#bordered=true&titled=true";

        return Map.of("iframeUrl", iframeUrl);
    }
}

