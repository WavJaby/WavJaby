package com.wavjaby.svgbuilder;

public class SvgText extends SvgComponent<SvgText> {

    public SvgText(String text) {
        super("text");
        appendChild(text);
    }

    public SvgText setY(int y) {
        this.attributes.put("y", y);
        return this;
    }

    public SvgText setX(int x) {
        this.attributes.put("x", x);
        return this;
    }

    @Override
    SvgText getThis() {
        return this;
    }
}
