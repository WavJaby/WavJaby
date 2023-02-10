package com.wavjaby.svgbuilder;

public class Svg extends SvgComponent<Svg> {
    final int width, height;
    SvgRect bgElement;

    public Svg(int width, int height) {
        super("svg");
        this.width = width;
        this.height = height;
        attributes.put("width", width);
        attributes.put("height", height);
        attributes.put("viewBox", "0 0 " + width + ' ' + height);
        attributes.put("xmlns", "http://www.w3.org/2000/svg");
        attributes.put("role", "img");
    }

    public Svg setBackgroundColor(String color) {
        if (bgElement == null) {
            bgElement = new SvgRect(0, 0, width, height, color);
            insertChild(bgElement, 0);
        } else
            bgElement.setColor(color);
        return this;
    }

    @Override
    Svg getThis() {
        return this;
    }
}
