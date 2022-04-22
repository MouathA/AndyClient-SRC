package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.*;

public final class AliasEvent extends NodeEvent
{
    public AliasEvent(final String s, final Mark mark, final Mark mark2) {
        super(s, mark, mark2);
        if (s == null) {
            throw new NullPointerException("anchor is not specified for alias");
        }
    }
    
    @Override
    public ID getEventId() {
        return ID.Alias;
    }
}
