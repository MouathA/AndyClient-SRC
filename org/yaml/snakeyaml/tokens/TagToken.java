package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.*;

public final class TagToken extends Token
{
    private final TagTuple value;
    
    public TagToken(final TagTuple value, final Mark mark, final Mark mark2) {
        super(mark, mark2);
        this.value = value;
    }
    
    public TagTuple getValue() {
        return this.value;
    }
    
    @Override
    public ID getTokenId() {
        return ID.Tag;
    }
}
