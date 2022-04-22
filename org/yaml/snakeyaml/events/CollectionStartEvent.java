package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.error.*;

public abstract class CollectionStartEvent extends NodeEvent
{
    private final String tag;
    private final boolean implicit;
    private final DumperOptions.FlowStyle flowStyle;
    
    public CollectionStartEvent(final String s, final String tag, final boolean implicit, final Mark mark, final Mark mark2, final DumperOptions.FlowStyle flowStyle) {
        super(s, mark, mark2);
        this.tag = tag;
        this.implicit = implicit;
        if (flowStyle == null) {
            throw new NullPointerException("Flow style must be provided.");
        }
        this.flowStyle = flowStyle;
    }
    
    @Deprecated
    public CollectionStartEvent(final String s, final String s2, final boolean b, final Mark mark, final Mark mark2, final Boolean b2) {
        this(s, s2, b, mark, mark2, DumperOptions.FlowStyle.fromBoolean(b2));
    }
    
    public String getTag() {
        return this.tag;
    }
    
    public boolean getImplicit() {
        return this.implicit;
    }
    
    public DumperOptions.FlowStyle getFlowStyle() {
        return this.flowStyle;
    }
    
    @Override
    protected String getArguments() {
        return super.getArguments() + ", tag=" + this.tag + ", implicit=" + this.implicit;
    }
    
    public boolean isFlow() {
        return DumperOptions.FlowStyle.FLOW == this.flowStyle;
    }
}
