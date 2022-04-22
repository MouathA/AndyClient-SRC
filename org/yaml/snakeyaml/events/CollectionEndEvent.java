package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.*;

public abstract class CollectionEndEvent extends Event
{
    public CollectionEndEvent(final Mark mark, final Mark mark2) {
        super(mark, mark2);
    }
}
