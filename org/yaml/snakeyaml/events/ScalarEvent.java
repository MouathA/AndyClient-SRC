package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.error.*;

public final class ScalarEvent extends NodeEvent
{
    private final String tag;
    private final DumperOptions.ScalarStyle style;
    private final String value;
    private final ImplicitTuple implicit;
    
    public ScalarEvent(final String s, final String tag, final ImplicitTuple implicit, final String value, final Mark mark, final Mark mark2, final DumperOptions.ScalarStyle style) {
        super(s, mark, mark2);
        this.tag = tag;
        this.implicit = implicit;
        if (value == null) {
            throw new NullPointerException("Value must be provided.");
        }
        this.value = value;
        if (style == null) {
            throw new NullPointerException("Style must be provided.");
        }
        this.style = style;
    }
    
    @Deprecated
    public ScalarEvent(final String s, final String s2, final ImplicitTuple implicitTuple, final String s3, final Mark mark, final Mark mark2, final Character c) {
        this(s, s2, implicitTuple, s3, mark, mark2, DumperOptions.ScalarStyle.createStyle(c));
    }
    
    public String getTag() {
        return this.tag;
    }
    
    public DumperOptions.ScalarStyle getScalarStyle() {
        return this.style;
    }
    
    @Deprecated
    public Character getStyle() {
        return this.style.getChar();
    }
    
    public String getValue() {
        return this.value;
    }
    
    public ImplicitTuple getImplicit() {
        return this.implicit;
    }
    
    @Override
    protected String getArguments() {
        return super.getArguments() + ", tag=" + this.tag + ", " + this.implicit + ", value=" + this.value;
    }
    
    @Override
    public ID getEventId() {
        return ID.Scalar;
    }
    
    public boolean isPlain() {
        return this.style == DumperOptions.ScalarStyle.PLAIN;
    }
}
