package com.wavjaby.svgbuilder;

public class AttrTranslate implements AttrTransform {
    private final int x, y;

    public AttrTranslate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "translate(" + x + ',' + y + ')';
    }
}
