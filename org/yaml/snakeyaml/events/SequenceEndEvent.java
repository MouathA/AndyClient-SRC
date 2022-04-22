package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.*;

public final class SequenceEndEvent extends CollectionEndEvent
{
    public SequenceEndEvent(final Mark mark, final Mark mark2) {
        super(mark, mark2);
    }
    
    @Override
    public ID getEventId() {
        return ID.SequenceEnd;
    }
}
