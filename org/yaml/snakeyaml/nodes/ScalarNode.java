package org.yaml.snakeyaml.nodes;

import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.error.*;

public class ScalarNode extends Node
{
    private DumperOptions.ScalarStyle style;
    private String value;
    
    public ScalarNode(final Tag tag, final String s, final Mark mark, final Mark mark2, final DumperOptions.ScalarStyle scalarStyle) {
        this(tag, true, s, mark, mark2, scalarStyle);
    }
    
    public ScalarNode(final Tag tag, final boolean resolved, final String value, final Mark mark, final Mark mark2, final DumperOptions.ScalarStyle style) {
        super(tag, mark, mark2);
        if (value == null) {
            throw new NullPointerException("value in a Node is required.");
        }
        this.value = value;
        if (style == null) {
            throw new NullPointerException("Scalar style must be provided.");
        }
        this.style = style;
        this.resolved = resolved;
    }
    
    @Deprecated
    public ScalarNode(final Tag tag, final String s, final Mark mark, final Mark mark2, final Character c) {
        this(tag, s, mark, mark2, DumperOptions.ScalarStyle.createStyle(c));
    }
    
    @Deprecated
    public ScalarNode(final Tag tag, final boolean b, final String s, final Mark mark, final Mark mark2, final Character c) {
        this(tag, b, s, mark, mark2, DumperOptions.ScalarStyle.createStyle(c));
    }
    
    @Deprecated
    public Character getStyle() {
        return this.style.getChar();
    }
    
    public DumperOptions.ScalarStyle getScalarStyle() {
        return this.style;
    }
    
    @Override
    public NodeId getNodeId() {
        return NodeId.scalar;
    }
    
    public String getValue() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return "<" + this.getClass().getName() + " (tag=" + this.getTag() + ", value=" + this.getValue() + ")>";
    }
    
    public boolean isPlain() {
        return this.style == DumperOptions.ScalarStyle.PLAIN;
    }
}
