package org.yaml.snakeyaml.tokens;

import java.util.*;
import org.yaml.snakeyaml.error.*;

public final class DirectiveToken extends Token
{
    private final String name;
    private final List value;
    
    public DirectiveToken(final String name, final List value, final Mark mark, final Mark mark2) {
        super(mark, mark2);
        this.name = name;
        if (value != null && value.size() != 2) {
            throw new YAMLException("Two strings must be provided instead of " + String.valueOf(value.size()));
        }
        this.value = value;
    }
    
    public String getName() {
        return this.name;
    }
    
    public List getValue() {
        return this.value;
    }
    
    @Override
    public ID getTokenId() {
        return ID.Directive;
    }
}
