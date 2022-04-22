package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.*;

public abstract class NodeEvent extends Event
{
    private final String anchor;
    
    public NodeEvent(final String anchor, final Mark mark, final Mark mark2) {
        super(mark, mark2);
        this.anchor = anchor;
    }
    
    public String getAnchor() {
        return this.anchor;
    }
    
    @Override
    protected String getArguments() {
        return "anchor=" + this.anchor;
    }
}
