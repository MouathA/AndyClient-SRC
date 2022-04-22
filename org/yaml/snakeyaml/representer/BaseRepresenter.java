package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.introspector.*;
import org.yaml.snakeyaml.error.*;
import java.util.*;
import org.yaml.snakeyaml.nodes.*;

public abstract class BaseRepresenter
{
    protected final Map representers;
    protected Represent nullRepresenter;
    protected final Map multiRepresenters;
    protected DumperOptions.ScalarStyle defaultScalarStyle;
    protected DumperOptions.FlowStyle defaultFlowStyle;
    protected final Map representedObjects;
    protected Object objectToRepresent;
    private PropertyUtils propertyUtils;
    private boolean explicitPropertyUtils;
    
    public BaseRepresenter() {
        this.representers = new HashMap();
        this.multiRepresenters = new LinkedHashMap();
        this.defaultScalarStyle = null;
        this.defaultFlowStyle = DumperOptions.FlowStyle.AUTO;
        this.representedObjects = new IdentityHashMap() {
            private static final long serialVersionUID = -5576159264232131854L;
            final BaseRepresenter this$0;
            
            public Node put(final Object o, final Node node) {
                return super.put(o, new AnchorNode(node));
            }
            
            @Override
            public Object put(final Object o, final Object o2) {
                return this.put(o, (Node)o2);
            }
        };
        this.explicitPropertyUtils = false;
    }
    
    public Node represent(final Object o) {
        final Node representData = this.representData(o);
        this.representedObjects.clear();
        this.objectToRepresent = null;
        return representData;
    }
    
    protected final Node representData(final Object objectToRepresent) {
        this.objectToRepresent = objectToRepresent;
        if (this.representedObjects.containsKey(this.objectToRepresent)) {
            return this.representedObjects.get(this.objectToRepresent);
        }
        if (objectToRepresent == null) {
            return this.nullRepresenter.representData(null);
        }
        final Class<?> class1 = objectToRepresent.getClass();
        Node node;
        if (this.representers.containsKey(class1)) {
            node = this.representers.get(class1).representData(objectToRepresent);
        }
        else {
            for (final Class clazz : this.multiRepresenters.keySet()) {
                if (clazz != null && clazz.isInstance(objectToRepresent)) {
                    return ((Represent)this.multiRepresenters.get(clazz)).representData(objectToRepresent);
                }
            }
            if (this.multiRepresenters.containsKey(null)) {
                node = this.multiRepresenters.get(null).representData(objectToRepresent);
            }
            else {
                node = this.representers.get(null).representData(objectToRepresent);
            }
        }
        return node;
    }
    
    protected Node representScalar(final Tag tag, final String s, DumperOptions.ScalarStyle defaultScalarStyle) {
        if (defaultScalarStyle == null) {
            defaultScalarStyle = this.defaultScalarStyle;
        }
        return new ScalarNode(tag, s, null, null, defaultScalarStyle);
    }
    
    protected Node representScalar(final Tag tag, final String s) {
        return this.representScalar(tag, s, null);
    }
    
    protected Node representSequence(final Tag tag, final Iterable iterable, final DumperOptions.FlowStyle flowStyle) {
        if (iterable instanceof List) {
            ((List<Object>)iterable).size();
        }
        final ArrayList<ScalarNode> list = new ArrayList<ScalarNode>(10);
        final SequenceNode sequenceNode = new SequenceNode(tag, list, flowStyle);
        this.representedObjects.put(this.objectToRepresent, sequenceNode);
        DumperOptions.FlowStyle flowStyle2 = DumperOptions.FlowStyle.FLOW;
        final Iterator<Object> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            final Node representData = this.representData(iterator.next());
            if (!(representData instanceof ScalarNode) || !((ScalarNode)representData).isPlain()) {
                flowStyle2 = DumperOptions.FlowStyle.BLOCK;
            }
            list.add((ScalarNode)representData);
        }
        if (flowStyle == DumperOptions.FlowStyle.AUTO) {
            if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
                sequenceNode.setFlowStyle(this.defaultFlowStyle);
            }
            else {
                sequenceNode.setFlowStyle(flowStyle2);
            }
        }
        return sequenceNode;
    }
    
    protected Node representMapping(final Tag tag, final Map map, final DumperOptions.FlowStyle flowStyle) {
        final ArrayList<NodeTuple> list = new ArrayList<NodeTuple>(map.size());
        final MappingNode mappingNode = new MappingNode(tag, list, flowStyle);
        this.representedObjects.put(this.objectToRepresent, mappingNode);
        DumperOptions.FlowStyle flowStyle2 = DumperOptions.FlowStyle.FLOW;
        for (final Map.Entry<Object, V> entry : map.entrySet()) {
            final Node representData = this.representData(entry.getKey());
            final Node representData2 = this.representData(entry.getValue());
            if (!(representData instanceof ScalarNode) || !((ScalarNode)representData).isPlain()) {
                flowStyle2 = DumperOptions.FlowStyle.BLOCK;
            }
            if (!(representData2 instanceof ScalarNode) || !((ScalarNode)representData2).isPlain()) {
                flowStyle2 = DumperOptions.FlowStyle.BLOCK;
            }
            list.add(new NodeTuple(representData, representData2));
        }
        if (flowStyle == DumperOptions.FlowStyle.AUTO) {
            if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
                mappingNode.setFlowStyle(this.defaultFlowStyle);
            }
            else {
                mappingNode.setFlowStyle(flowStyle2);
            }
        }
        return mappingNode;
    }
    
    public void setDefaultScalarStyle(final DumperOptions.ScalarStyle defaultScalarStyle) {
        this.defaultScalarStyle = defaultScalarStyle;
    }
    
    public DumperOptions.ScalarStyle getDefaultScalarStyle() {
        if (this.defaultScalarStyle == null) {
            return DumperOptions.ScalarStyle.PLAIN;
        }
        return this.defaultScalarStyle;
    }
    
    public void setDefaultFlowStyle(final DumperOptions.FlowStyle defaultFlowStyle) {
        this.defaultFlowStyle = defaultFlowStyle;
    }
    
    public DumperOptions.FlowStyle getDefaultFlowStyle() {
        return this.defaultFlowStyle;
    }
    
    public void setPropertyUtils(final PropertyUtils propertyUtils) {
        this.propertyUtils = propertyUtils;
        this.explicitPropertyUtils = true;
    }
    
    public final PropertyUtils getPropertyUtils() {
        if (this.propertyUtils == null) {
            this.propertyUtils = new PropertyUtils();
        }
        return this.propertyUtils;
    }
    
    public final boolean isExplicitPropertyUtils() {
        return this.explicitPropertyUtils;
    }
}
