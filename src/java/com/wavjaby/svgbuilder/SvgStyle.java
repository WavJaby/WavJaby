package com.wavjaby.svgbuilder;

import java.util.HashMap;
import java.util.Map;

public class SvgStyle extends XmlComponent {

    private final HashMap<String, String> styles = new HashMap<>();

    public SvgStyle() {
        super("style");
    }

    public SvgStyle addStyle(String selector, String styles) {
        this.styles.put(selector, styles);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> e : styles.entrySet())
            builder.append(e.getKey()).append('{').append(e.getValue()).append('}');
        child.add(builder.toString());
        return super.toString();
    }
}
