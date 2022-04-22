package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.*;

public class WhitespaceToken extends Token
{
    public WhitespaceToken(final Mark mark, final Mark mark2) {
        super(mark, mark2);
    }
    
    @Override
    public ID getTokenId() {
        return ID.Whitespace;
    }
}
