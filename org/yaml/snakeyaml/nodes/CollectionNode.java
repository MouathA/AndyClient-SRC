package org.yaml.snakeyaml.nodes;

import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.error.*;
import java.util.*;

public abstract class CollectionNode extends Node
{
    private DumperOptions.FlowStyle flowStyle;
    
    public CollectionNode(final Tag tag, final Mark mark, final Mark mark2, final DumperOptions.FlowStyle flowStyle) {
        super(tag, mark, mark2);
        this.setFlowStyle(flowStyle);
    }
    
    @Deprecated
    public CollectionNode(final Tag tag, final Mark mark, final Mark mark2, final Boolean b) {
        this(tag, mark, mark2, DumperOptions.FlowStyle.fromBoolean(b));
    }
    
    public abstract List getValue();
    
    public DumperOptions.FlowStyle getFlowStyle() {
        return this.flowStyle;
    }
    
    public void setFlowStyle(final DumperOptions.FlowStyle flowStyle) {
        if (flowStyle == null) {
            throw new NullPointerException("Flow style must be provided.");
        }
        this.flowStyle = flowStyle;
    }
    
    @Deprecated
    public void setFlowStyle(final Boolean b) {
        this.setFlowStyle(DumperOptions.FlowStyle.fromBoolean(b));
    }
    
    public void setEndMark(final Mark endMark) {
        this.endMark = endMark;
    }
}
