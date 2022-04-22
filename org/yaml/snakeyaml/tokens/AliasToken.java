package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.*;

public final class AliasToken extends Token
{
    private final String value;
    
    public AliasToken(final String value, final Mark mark, final Mark mark2) {
        super(mark, mark2);
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
    
    @Override
    public ID getTokenId() {
        return ID.Alias;
    }
}
