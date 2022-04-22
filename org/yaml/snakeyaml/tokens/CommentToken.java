package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.*;

public class CommentToken extends Token
{
    public CommentToken(final Mark mark, final Mark mark2) {
        super(mark, mark2);
    }
    
    @Override
    public ID getTokenId() {
        return ID.Comment;
    }
}
