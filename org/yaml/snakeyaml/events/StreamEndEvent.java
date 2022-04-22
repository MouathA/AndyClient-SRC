package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.*;

public final class StreamEndEvent extends Event
{
    public StreamEndEvent(final Mark mark, final Mark mark2) {
        super(mark, mark2);
    }
    
    @Override
    public ID getEventId() {
        return ID.StreamEnd;
    }
}
