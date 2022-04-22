package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.*;

public final class SequenceStartEvent extends CollectionStartEvent
{
    public SequenceStartEvent(final String s, final String s2, final boolean b, final Mark mark, final Mark mark2, final DumperOptions.FlowStyle flowStyle) {
        super(s, s2, b, mark, mark2, flowStyle);
    }
    
    @Deprecated
    public SequenceStartEvent(final String s, final String s2, final boolean b, final Mark mark, final Mark mark2, final Boolean b2) {
        this(s, s2, b, mark, mark2, DumperOptions.FlowStyle.fromBoolean(b2));
    }
    
    @Override
    public ID getEventId() {
        return ID.SequenceStart;
    }
}
