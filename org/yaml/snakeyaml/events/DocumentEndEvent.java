package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.*;

public final class DocumentEndEvent extends Event
{
    private final boolean explicit;
    
    public DocumentEndEvent(final Mark mark, final Mark mark2, final boolean explicit) {
        super(mark, mark2);
        this.explicit = explicit;
    }
    
    public boolean getExplicit() {
        return this.explicit;
    }
    
    @Override
    public ID getEventId() {
        return ID.DocumentEnd;
    }
}
