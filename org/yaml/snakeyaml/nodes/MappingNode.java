package org.yaml.snakeyaml.nodes;

import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.*;
import java.util.*;

public class MappingNode extends CollectionNode
{
    private List value;
    private boolean merged;
    
    public MappingNode(final Tag tag, final boolean resolved, final List value, final Mark mark, final Mark mark2, final DumperOptions.FlowStyle flowStyle) {
        super(tag, mark, mark2, flowStyle);
        this.merged = false;
        if (value == null) {
            throw new NullPointerException("value in a Node is required.");
        }
        this.value = value;
        this.resolved = resolved;
    }
    
    public MappingNode(final Tag tag, final List list, final DumperOptions.FlowStyle flowStyle) {
        this(tag, true, list, null, null, flowStyle);
    }
    
    @Deprecated
    public MappingNode(final Tag tag, final boolean b, final List list, final Mark mark, final Mark mark2, final Boolean b2) {
        this(tag, b, list, mark, mark2, DumperOptions.FlowStyle.fromBoolean(b2));
    }
    
    @Deprecated
    public MappingNode(final Tag tag, final List list, final Boolean b) {
        this(tag, list, DumperOptions.FlowStyle.fromBoolean(b));
    }
    
    @Override
    public NodeId getNodeId() {
        return NodeId.mapping;
    }
    
    @Override
    public List getValue() {
        return this.value;
    }
    
    public void setValue(final List value) {
        this.value = value;
    }
    
    public void setOnlyKeyType(final Class type) {
        final Iterator<NodeTuple> iterator = this.value.iterator();
        while (iterator.hasNext()) {
            iterator.next().getKeyNode().setType(type);
        }
    }
    
    public void setTypes(final Class type, final Class type2) {
        for (final NodeTuple nodeTuple : this.value) {
            nodeTuple.getValueNode().setType(type2);
            nodeTuple.getKeyNode().setType(type);
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final NodeTuple nodeTuple : this.getValue()) {
            sb.append("{ key=");
            sb.append(nodeTuple.getKeyNode());
            sb.append("; value=");
            if (nodeTuple.getValueNode() instanceof CollectionNode) {
                sb.append(System.identityHashCode(nodeTuple.getValueNode()));
            }
            else {
                sb.append(nodeTuple.toString());
            }
            sb.append(" }");
        }
        return "<" + this.getClass().getName() + " (tag=" + this.getTag() + ", values=" + sb.toString() + ")>";
    }
    
    public void setMerged(final boolean merged) {
        this.merged = merged;
    }
    
    public boolean isMerged() {
        return this.merged;
    }
}
