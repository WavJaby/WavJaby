package com.wavjaby.github.obj;

import com.wavjaby.github.Api;
import com.wavjaby.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class Languages {
    final Map<String, Long> usedLanguages;

    public Languages(JsonObject data, Api api) {
        usedLanguages = new HashMap<>();
        for (Map.Entry<String, Object> i : data)
            usedLanguages.put(i.getKey(), ((Number) i.getValue()).longValue());
    }

    public Map<String, Long> getLanguages() {
        return usedLanguages;
    }
}

