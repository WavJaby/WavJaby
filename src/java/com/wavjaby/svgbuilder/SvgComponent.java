package com.wavjaby.svgbuilder;

public abstract class SvgComponent<T> extends XmlComponent {
    public SvgComponent(String tag) {
        super(tag);
    }

    public T setClass(String className) {
        this.attributes.put("class", className);
        return getThis();
    }

    public T setStyle(AttrStyle styles) {
        this.attributes.put("style", styles.toString());
        return getThis();
    }

    public T appendChild(Object child) {
        this.child.add(child);
        return getThis();
    }

    public T insertChild(Object child, int index) {
        this.child.add(index, child);
        return getThis();
    }

    abstract T getThis();
}