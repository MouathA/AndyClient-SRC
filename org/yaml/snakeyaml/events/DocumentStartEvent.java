package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.*;
import java.util.*;
import org.yaml.snakeyaml.error.*;

public final class DocumentStartEvent extends Event
{
    private final boolean explicit;
    private final DumperOptions.Version version;
    private final Map tags;
    
    public DocumentStartEvent(final Mark mark, final Mark mark2, final boolean explicit, final DumperOptions.Version version, final Map tags) {
        super(mark, mark2);
        this.explicit = explicit;
        this.version = version;
        this.tags = tags;
    }
    
    public boolean getExplicit() {
        return this.explicit;
    }
    
    public DumperOptions.Version getVersion() {
        return this.version;
    }
    
    public Map getTags() {
        return this.tags;
    }
    
    @Override
    public ID getEventId() {
        return ID.DocumentStart;
    }
}
