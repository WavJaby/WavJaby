package com.wavjaby.svgbuilder;

import java.util.HashMap;
import java.util.stream.Collectors;

public class AttrStyle {

    private final HashMap<String, String> styles = new HashMap<>();

    public AttrStyle addStyle(String name, String value) {
        styles.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return styles.entrySet().stream().map(e -> e.getKey() + ':' + e.getValue()).collect(Collectors.joining(";"));
    }
}
