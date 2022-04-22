package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.*;

public final class StreamStartEvent extends Event
{
    public StreamStartEvent(final Mark mark, final Mark mark2) {
        super(mark, mark2);
    }
    
    @Override
    public ID getEventId() {
        return ID.StreamStart;
    }
}
