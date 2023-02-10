package com.wavjaby.svgbuilder;

import java.util.*;

public abstract class XmlComponent {
    final String tag;
    final Map<String, Object> attributes = new LinkedHashMap<>();
    final List<Object> child = new ArrayList<>();

    XmlComponent(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('<').append(tag);
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            builder.append(' ').append(entry.getKey()).append('=');
            quote(entry.getValue().toString(), builder);
        }
        if (this.child.size() == 0) {
            builder.append('/').append('>');
        } else {
            builder.append('>');
            for (Object child : this.child)
                builder.append(child);
            builder.append('<').append('/').append(tag).append('>');
        }
        return builder.toString();
    }

    private static void quote(String string, StringBuilder builder) {
        if (string.isEmpty()) {
            builder.append("\"\"");
            return;
        }

        char lastChar;
        char thisChar = 0;
        String unicode;
        int len = string.length();

        builder.append('"');
        for (int i = 0; i < len; i++) {
            lastChar = thisChar;
            thisChar = string.charAt(i);
            switch (thisChar) {
                case '\\':
                case '"':
                    builder.append('\\');
                    builder.append(thisChar);
                    break;
                case '/':
                    if (lastChar == '<')
                        builder.append('\\');
                    builder.append(thisChar);
                    break;
                case '\b':
                    builder.append("\\b");
                    break;
                case '\t':
                    builder.append("\\t");
                    break;
                case '\n':
                    builder.append("\\n");
                    break;
                case '\f':
                    builder.append("\\f");
                    break;
                case '\r':
                    builder.append("\\r");
                    break;
                default:
                    if (thisChar < ' ' || (thisChar >= '\u0080' && thisChar < '\u00a0') || (thisChar >= '\u2000' && thisChar < '\u2100')) {
                        builder.append("\\u");
                        unicode = Integer.toHexString(thisChar);
                        builder.append("0000", 0, 4 - unicode.length());
                        builder.append(unicode);
                    } else
                        builder.append(thisChar);
            }
        }
        builder.append('"');
    }
}
