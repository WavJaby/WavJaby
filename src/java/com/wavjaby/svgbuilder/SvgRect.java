package com.wavjaby.svgbuilder;

public class SvgRect extends SvgComponent<SvgRect> {

    public SvgRect(int x, int y, int width, int height) {
        super("rect");
        attributes.put("x", x);
        attributes.put("y", y);
        attributes.put("width", width);
        attributes.put("height", height);
    }

    public SvgRect(int x, int y, int width, int height, String fill) {
        super("rect");
        attributes.put("x", x);
        attributes.put("y", y);
        attributes.put("width", width);
        attributes.put("height", height);
        attributes.put("fill", fill);
    }

    public SvgRect setColor(String color){
        attributes.put("fill", color);
        return this;
    }

    public SvgRect setBorderRadius(int radius) {
        attributes.put("rx", radius);
        return this;
    }

    @Override
    SvgRect getThis() {
        return this;
    }
}
