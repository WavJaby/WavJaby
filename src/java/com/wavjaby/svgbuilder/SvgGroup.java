package com.wavjaby.svgbuilder;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SvgGroup extends SvgComponent<SvgGroup> {
    public SvgGroup() {
        super("g");
    }

    public SvgGroup setTransform(AttrTransform ...transform) {
        attributes.put("transform", Arrays.stream(transform).map(Object::toString).collect(Collectors.joining(" ")));
        return this;
    }

    @Override
    SvgGroup getThis() {
        return this;
    }
}
