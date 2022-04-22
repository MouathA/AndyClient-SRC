package org.yaml.snakeyaml.nodes;

import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.*;
import java.util.*;

public class SequenceNode extends CollectionNode
{
    private final List value;
    
    public SequenceNode(final Tag tag, final boolean resolved, final List value, final Mark mark, final Mark mark2, final DumperOptions.FlowStyle flowStyle) {
        super(tag, mark, mark2, flowStyle);
        if (value == null) {
            throw new NullPointerException("value in a Node is required.");
        }
        this.value = value;
        this.resolved = resolved;
    }
    
    public SequenceNode(final Tag tag, final List list, final DumperOptions.FlowStyle flowStyle) {
        this(tag, true, list, null, null, flowStyle);
    }
    
    @Deprecated
    public SequenceNode(final Tag tag, final List list, final Boolean b) {
        this(tag, list, DumperOptions.FlowStyle.fromBoolean(b));
    }
    
    @Deprecated
    public SequenceNode(final Tag tag, final boolean b, final List list, final Mark mark, final Mark mark2, final Boolean b2) {
        this(tag, b, list, mark, mark2, DumperOptions.FlowStyle.fromBoolean(b2));
    }
    
    @Override
    public NodeId getNodeId() {
        return NodeId.sequence;
    }
    
    @Override
    public List getValue() {
        return this.value;
    }
    
    public void setListType(final Class type) {
        final Iterator<Node> iterator = this.value.iterator();
        while (iterator.hasNext()) {
            iterator.next().setType(type);
        }
    }
    
    @Override
    public String toString() {
        return "<" + this.getClass().getName() + " (tag=" + this.getTag() + ", value=" + this.getValue() + ")>";
    }
}
