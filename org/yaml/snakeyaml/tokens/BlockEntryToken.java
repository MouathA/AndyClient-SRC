package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.*;

public final class BlockEntryToken extends Token
{
    public BlockEntryToken(final Mark mark, final Mark mark2) {
        super(mark, mark2);
    }
    
    @Override
    public ID getTokenId() {
        return ID.BlockEntry;
    }
}