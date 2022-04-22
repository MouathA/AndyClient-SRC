package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.*;

public final class StreamEndToken extends Token
{
    public StreamEndToken(final Mark mark, final Mark mark2) {
        super(mark, mark2);
    }
    
    @Override
    public ID getTokenId() {
        return ID.StreamEnd;
    }
}
