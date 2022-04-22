package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.error.*;

public final class ScalarToken extends Token
{
    private final String value;
    private final boolean plain;
    private final DumperOptions.ScalarStyle style;
    
    public ScalarToken(final String s, final Mark mark, final Mark mark2, final boolean b) {
        this(s, b, mark, mark2, DumperOptions.ScalarStyle.PLAIN);
    }
    
    public ScalarToken(final String value, final boolean plain, final Mark mark, final Mark mark2, final DumperOptions.ScalarStyle style) {
        super(mark, mark2);
        this.value = value;
        this.plain = plain;
        if (style == null) {
            throw new NullPointerException("Style must be provided.");
        }
        this.style = style;
    }
    
    public boolean getPlain() {
        return this.plain;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public DumperOptions.ScalarStyle getStyle() {
        return this.style;
    }
    
    @Override
    public ID getTokenId() {
        return ID.Scalar;
    }
}
